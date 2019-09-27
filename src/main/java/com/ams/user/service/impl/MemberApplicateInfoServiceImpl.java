package com.ams.user.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ams.department.entity.Department;
import com.ams.department.entity.Job;
import com.ams.user.dao.MemberApplicateInfoDao;
import com.ams.user.entity.MemberApplicateInfo;
import com.ams.user.service.IMemberApplicateInfoService;
import com.ams.utils.IdGen;
@Service("memberApplicateInfoService")
public class MemberApplicateInfoServiceImpl implements IMemberApplicateInfoService{
	@Resource
	private MemberApplicateInfoDao memberApplicateInfoDao;
	
	public MemberApplicateInfo getMemberApplicateInfoById(String MemberApplicateInfoId) {
		return this.memberApplicateInfoDao.selectByPrimaryKey(MemberApplicateInfoId);
	}

	public List<MemberApplicateInfo> getAllApplicateInfoList(String queryInfo, int offset, int limit) {
		return this.memberApplicateInfoDao.getAllApplicateInfoList(queryInfo,offset,limit);
	}

	public int getAllApplicateCount() {
		return this.memberApplicateInfoDao.getAllApplicateCount();
	}

	@Override
	public int updateApplicateSuccessStatus(List<String> idList) {
		return this.memberApplicateInfoDao. updateApplicateSuccessStatus(idList);
	}

	@Override
	public int updateApplicateFailStatus(List<String> idList) {
		return this.memberApplicateInfoDao. updateApplicateFailStatus(idList);
	}

	@Override
	public int addInfoByMemberApplicateInfo(MemberApplicateInfo memberApplicateInfo,Department department,Job job) {
		String id=IdGen.uuid();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime=df.format(new Date());
		memberApplicateInfo.setApplicateTime(createTime);
		memberApplicateInfo.setId(id);
		memberApplicateInfo.setApplicateSection(department.getId());
		memberApplicateInfo.setApplicateJob(job.getId());
		return this.memberApplicateInfoDao.insertSelective(memberApplicateInfo);
	}

	@Override
	public MemberApplicateInfo getMemberApplicateInfoByDigits(String digits) {
		return this.memberApplicateInfoDao.getMemberApplicateInfoByDigit(digits);
	}

}
