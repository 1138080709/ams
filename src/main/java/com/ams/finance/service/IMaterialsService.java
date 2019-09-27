package com.ams.finance.service;

import java.util.List;

import com.ams.finance.dao.MaterialFlowInfoDao;
import com.ams.finance.entity.MaterialFlowInfo;
import com.ams.finance.entity.Materials;

public interface IMaterialsService {

	int saveMaterialInfo(Materials materials);

	Materials getMaterialsById(String id);

	int updateMaterialsInfo(Materials materials);

	int getMaterialsCount();

	List<Materials> getMaterialsList(String queryInfo, int offset, int limit);

	int getMaterialsFlowInfoCount();

	List<MaterialFlowInfoDao> getMaterialFlowInfoList(String queryInfo, int offset, int limit);

	int addMaterialFlowInfo(MaterialFlowInfo materialFlowInfo, String id);

	int updateMaterialNumber(MaterialFlowInfo materialFlowInfo);

	int updateMaterialsDelFlag(List<String> idList);
}
