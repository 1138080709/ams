package com.ams.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ams.user.entity.MemberApplicateInfo;

public interface MemberApplicateInfoDao {

	List<MemberApplicateInfo> getAllApplicateInfoList(@Param("queryInfo")String queryInfo, @Param("offset")int offset, @Param("limit")int limit);

	int getAllApplicateCount();

	int updateApplicateSuccessStatus(List<String> idList);
	
	int updateApplicateFailStatus(List<String> idList);

	MemberApplicateInfo getMemberApplicateInfoByDigit(String digits);
	
	int insertSelective(MemberApplicateInfo record);
	
	MemberApplicateInfo selectMemberApplicateInfoById(String id);

	MemberApplicateInfo getInfoById(String memberApplicateInfoId);
}