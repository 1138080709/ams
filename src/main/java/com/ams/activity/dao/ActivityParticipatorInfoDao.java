package com.ams.activity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ams.activity.entity.ActivityParticipatorInfo;

public interface ActivityParticipatorInfoDao {

	ActivityParticipatorInfo duplicateChecking(@Param("digits")String digits, @Param("activityId")String activityId);

	int insert(ActivityParticipatorInfo activityParticipatorInfo);

	List<ActivityParticipatorInfo> getApplyListById(String id);
}