package com.ams.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ams.user.dao.MemberApplicateInfoDao;
import com.ams.user.entity.MemberApplicateInfo;
import com.ams.user.service.IMemberApplicateInfoService;
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

}
