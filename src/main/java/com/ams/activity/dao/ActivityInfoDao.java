package com.ams.activity.dao;

import com.ams.activity.entity.ActivityInfo;

public interface ActivityInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(ActivityInfo record);

    int insertSelective(ActivityInfo record);

    ActivityInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ActivityInfo record);

    int updateByPrimaryKey(ActivityInfo record);
}