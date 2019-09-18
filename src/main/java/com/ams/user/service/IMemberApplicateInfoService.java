package com.ams.user.service;

import java.util.List;

import com.ams.user.entity.MemberApplicateInfo;

public interface IMemberApplicateInfoService {
	public MemberApplicateInfo getMemberApplicateInfoById(String MemberApplicateInfoId);

	public List<MemberApplicateInfo> getAllApplicateInfoList(String queryInfo, int offset, int limit);

	public int getAllApplicateCount();

	public int updateApplicateSuccessStatus(List<String> idList);
	
	public int updateApplicateFailStatus(List<String> idList);
}
