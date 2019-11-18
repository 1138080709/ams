package com.ams.finance.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ams.finance.dao.MaterialFlowInfoDao;
import com.ams.finance.dao.MaterialsDao;
import com.ams.finance.entity.MaterialFlowInfo;
import com.ams.finance.entity.Materials;
import com.ams.finance.service.IMaterialsService;
import com.ams.user.dao.UserDao;
import com.ams.utils.IdGen;

@Service("materialsService")
public class MaterialsServiceImpl implements IMaterialsService{
	
	@Resource
	private MaterialsDao materialsDao;
	@Resource
	private MaterialFlowInfoDao materialsFlowInfoDao;
	@Resource
	private UserDao userDao;

	@Override
	public int saveMaterialInfo(Materials materials) {
		String id=IdGen.uuid();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime=df.format(new Date());
		materials.setId(id);
		materials.setRemainNumber(materials.getTotalNumber());
		materials.setCreateTime(createTime);
		return this.materialsDao.insertSelective(materials);
	}

	@Override
	public Materials getMaterialsById(String id) {
		return this.materialsDao.selectById(id);
	}

	@Override
	public int updateMaterialsInfo(Materials materials) {
		return this.materialsDao.updateMaterials(materials);
	}

	@Override
	public int getMaterialsCount() {
		return this.materialsDao.getMaterialsCount();
	}

	@Override
	public List<Materials> getMaterialsList(String queryInfo) {
		return this.materialsDao.getMaterialsList(queryInfo);
	}

	@Override
	public int getMaterialsFlowInfoCount() {
		return this.materialsFlowInfoDao.getMaterialsFlowInfoCount();
	}

	@Override
	public List<MaterialFlowInfoDao> getMaterialFlowInfoList(String queryInfo) {
		return this.materialsFlowInfoDao.getMaterialFlowInfoList(queryInfo);
	}

	@Override
	public int addMaterialFlowInfo(MaterialFlowInfo materialFlowInfo,String auditorId) {
		String id=IdGen.uuid();
		materialFlowInfo.setId(id);
		materialFlowInfo.setAuditorId(auditorId);
		return this.materialsFlowInfoDao.insertSelective(materialFlowInfo);
	}

	@Override
	public int updateMaterialNumber(MaterialFlowInfo materialFlowInfo) {
		Materials materials=materialsDao.selectById(materialFlowInfo.getMaterialId());
		if(materials==null)
			return 0;
		if(materialFlowInfo.getInfoType()==0) {
			if((materials.getRemainNumber()-materialFlowInfo.getNumber())<0)
				return -1;
			materials.setRemainNumber(materials.getRemainNumber()-materialFlowInfo.getNumber());
		}else if(materialFlowInfo.getInfoType()==1) {
			materials.setRemainNumber(materials.getRemainNumber()+materialFlowInfo.getNumber());
		}else
			return 0;
		return this.materialsDao.updateMaterials(materials);
				
	}

	@Override
	public int updateMaterialsDelFlag(List<String> idList) {
		return this.materialsDao.updateMaterialsDelFlag(idList);
	}
	
	
	
}
