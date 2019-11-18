package com.ams.activity.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ams.activity.dao.ActivityParticipatorInfoDao;
import com.ams.activity.entity.ActivityParticipatorInfo;
import com.ams.activity.service.IActivityParticipatorInfoService;
import com.ams.utils.IdGen;

@Service("activityParticipatorInfoService")
public class ActivityParticipatorInfoServiceImpl implements IActivityParticipatorInfoService {

	@Resource
	private ActivityParticipatorInfoDao activityParticipatorInfoDao;
	
	@Override
	public int applyActivity(ActivityParticipatorInfo activityParticipatorInfo) {
		String digits=activityParticipatorInfo.getParticipatorDigits();
		String activityId=activityParticipatorInfo.getActivityId();
		ActivityParticipatorInfo applyInfo=this.activityParticipatorInfoDao.duplicateChecking(digits,activityId);
		if(applyInfo!=null)
			return -1;
		activityParticipatorInfo.setId(IdGen.uuid());
		int result=activityParticipatorInfoDao.insert(activityParticipatorInfo);
			return result;
	}

	@Override
	public List<ActivityParticipatorInfo> getApplyListById(String id) {
		return this.activityParticipatorInfoDao.getApplyListById(id);
	}
		
}
