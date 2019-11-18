package com.ams.activity.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import com.ams.activity.service.IActivityInfoService;
import com.ams.activity.dao.ActivityInfoDao;
import com.ams.activity.entity.ActivityInfo;

@Service("activityInfoService")
public class ActivityInfoServiceImpl implements IActivityInfoService {
	@Resource
	private ActivityInfoDao activityInfoDao;

	@Override
	public int releaseActivity(ActivityInfo activityInfo) {
		return this.activityInfoDao.insert(activityInfo);
	}

	@Override
	public ActivityInfo selectActivityById(String id) {
		// TODO Auto-generated method stub
		return this.activityInfoDao.selectByPrimaryKey(id);
	}

	@Override
	public int deleteActivity(String id,int delFlag) {
		// TODO Auto-generated method stub
		return this.activityInfoDao.deleteByPrimaryKey(id,delFlag);
	}

	@Override
	public int modifyActivity(ActivityInfo activityInfo) {
		// TODO Auto-generated method stub
		return this.activityInfoDao.updateByPrimaryKey(activityInfo);
	}

	@Override
	public List<ActivityInfo> getActivityList() {
		// TODO Auto-generated method stub
		return this.activityInfoDao.selectAllActivity();
	}

	@Override
	public List<ActivityInfo> getHistoryList() {
		return this.activityInfoDao.getHistoryList();
	}

	
	
}
