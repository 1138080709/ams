package com.ams.activity.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ams.activity.entity.ActivityInfo;
import com.ams.utils.Result;

@Controller
@RequestMapping("activity")
public class ActivityController {
	
	/**
	 * 活动登记
	 * 
	 * @param activity
	 * @param request
	 * @return
	 */
	@RequestMapping("registerActivity")
	@ResponseBody
	public Result registerActivity(ActivityInfo activity,HttpServletRequest request) {
		return Result.makeSuccessResult();
	}
	
	/**
	 * 获取活动登记列表
	 * 
	 * @param queryInfo
	 * @param offset
	 * @param limit
	 * @param request
	 * @return
	 */
	@RequestMapping("getRegisterList")
	@ResponseBody
	public Result getRegisterList(String queryInfo,int offset,int limit,HttpServletRequest request) {
		return Result.makeSuccessResult();
	}
	
	/**
	 * 活动发布操作
	 * 
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping("activityReleased")
	@ResponseBody
	public Result activityReleased(String[] ids,HttpServletRequest request) {
		return Result.makeSuccessResult();
	}
	
	/**
	 * 活动取消发布
	 * 
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping("cancelReleased")
	@ResponseBody
	public Result cancelReleased(String[] ids,HttpServletRequest request) {
		return Result.makeSuccessResult();
	}
	
	/**
	 * 获取近期活动列表
	 * 
	 * @param queryInfo
	 * @param offset
	 * @param limit
	 * @return
	 */
	@RequestMapping("currentActivityHistory")
	@ResponseBody
	public Result currentActivityHistory(String queryInfo,int offset,int limit) {
		return Result.makeSuccessResult();
	}
	 
	
	/**
	 * 获取活动历史
	 * 
	 * @param queryInfo
	 * @param offset
	 * @param limit
	 * @return
	 */
	@RequestMapping("getActivityHistory")
	@ResponseBody
	public Result getActivityHistory(String queryInfo,int offset,int limit) {
		return Result.makeSuccessResult();
	}
}
