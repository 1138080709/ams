package com.ams.activity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ams.activity.entity.ActivityInfo;

public interface ActivityInfoDao {
    int deleteByPrimaryKey(@Param("id")String id,@Param("delFlag")int delFlag);

    int insert(ActivityInfo record);

    int insertSelective(ActivityInfo record);

    ActivityInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ActivityInfo record);

    int updateByPrimaryKey(ActivityInfo record);

	List<ActivityInfo> selectAllActivity();

	List<ActivityInfo> getHistoryList();
	
	
}