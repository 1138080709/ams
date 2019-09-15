package com.ams.user.dao;

import com.ams.user.entity.MemberApplicateInfo;

public interface MemberApplicateInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(MemberApplicateInfo record);

    int insertSelective(MemberApplicateInfo record);

    MemberApplicateInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MemberApplicateInfo record);

    int updateByPrimaryKey(MemberApplicateInfo record);
}