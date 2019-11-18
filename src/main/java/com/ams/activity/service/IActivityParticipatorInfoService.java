package com.ams.activity.service;

import java.util.List;

import com.ams.activity.entity.ActivityParticipatorInfo;

public interface IActivityParticipatorInfoService {

	int applyActivity(ActivityParticipatorInfo activityParticipatorInfo);

	List<ActivityParticipatorInfo> getApplyListById(String id);

}
