package com.ams.activity.dao;

import com.ams.activity.entity.ActivityParticipatorInfo;

public interface ActivityParticipatorInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(ActivityParticipatorInfo record);

    int insertSelective(ActivityParticipatorInfo record);

    ActivityParticipatorInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ActivityParticipatorInfo record);

    int updateByPrimaryKey(ActivityParticipatorInfo record);
}