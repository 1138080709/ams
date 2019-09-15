package com.ams.user.service.impl;

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

}
