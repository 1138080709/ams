package com.ams.user.service;

import java.util.List;

import com.ams.department.entity.Department;
import com.ams.department.entity.Job;
import com.ams.user.dto.MemberApplicateInfoDTO;
import com.ams.user.entity.MemberApplicateInfo;

public interface IMemberApplicateInfoService {
	public MemberApplicateInfo getMemberApplicateInfoById(String MemberApplicateInfoId);

	public List<MemberApplicateInfoDTO> getAllApplicateInfoList();

	public List<String> updateApplicateSuccessStatus(List<String> idList);
	
	public List<String> updateApplicateFailStatus(List<String> idList);

	public int addInfoByMemberApplicateInfo(MemberApplicateInfo memberApplicateInfo,Department department,Job job);

	public MemberApplicateInfo getMemberApplicateInfoByDigits(String digits);
	
	public boolean sendSuccessMail(String mail);
	
	public boolean sendFailMail(String mail);
}
