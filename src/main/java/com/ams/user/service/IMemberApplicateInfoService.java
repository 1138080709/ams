package com.ams.user.service;

import java.util.List;

import com.ams.department.entity.Department;
import com.ams.department.entity.Job;
import com.ams.user.entity.MemberApplicateInfo;

public interface IMemberApplicateInfoService {
	public MemberApplicateInfo getMemberApplicateInfoById(String MemberApplicateInfoId);

	public List<MemberApplicateInfo> getAllApplicateInfoList(String queryInfo, int offset, int limit);

	public int getAllApplicateCount();

	public int updateApplicateSuccessStatus(List<String> idList);
	
	public int updateApplicateFailStatus(List<String> idList);

	public int addInfoByMemberApplicateInfo(MemberApplicateInfo memberApplicateInfo,Department department,Job job);

	public MemberApplicateInfo getMemberApplicateInfoByDigits(String digits);
}
