package com.ams.activity.service;

import java.util.List;

import com.ams.activity.entity.ActivityInfo;
import com.ams.activity.entity.ActivityParticipatorInfo;

public interface IActivityInfoService {

	public int releaseActivity(ActivityInfo activityInfo);

	public ActivityInfo selectActivityById(String id);

	public int deleteActivity(String id,int delFlag);

	public int modifyActivity(ActivityInfo activityInfo);

	public List<ActivityInfo> getActivityList();

	public List<ActivityInfo> getHistoryList();



}
