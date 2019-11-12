package com.ams.activity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ams.activity.entity.ActivityInfo;
import com.ams.activity.service.IActivityInfoService;
import com.ams.utils.IdGen;
import com.ams.utils.Result;

@Controller
@RequestMapping("/activity")
public class ActivityController {
	@Resource
	private IActivityInfoService activityInfoService;
	
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
	 * @param activityInfo
	 * @param request
	 * @return
	 */
	@RequestMapping("releaseActivity")
	@ResponseBody
	public Result activityReleased(ActivityInfo activityInfo,HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		int releaseResult = 0;
		//判断各字段值是否为空
		if(activityInfo.getActivityTheme() == null || activityInfo.getPrincipalName() == null) {
			return Result.makeFailResult("活动名和活动主题不能为空！");
		}
		if(activityInfo.getDetailInfo() == null) {
			return Result.makeFailResult("请输入活动详细介绍！");
		}
		if(activityInfo.getOverTime() == null || activityInfo.getStartTime() == null) {
			return Result.makeFailResult("活动开始时间和结束时间不能为空！");
		}
		//执行数据库添加操作
		activityInfo.setId(IdGen.uuid());
		activityInfo.setActivityFlag(0);
		activityInfo.setDelFlag(0);
		releaseResult = this.activityInfoService.releaseActivity(activityInfo);
		if(releaseResult == 0) {
			return Result.makeFailResult("添加活动失败！");
		}
		//执行成功
		resultMap.put("releaseResult", releaseResult);
		return Result.makeSuccessResult(resultMap);
	}
	
	/**
	 * 活动取消发布
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("cancelReleased")
	@ResponseBody
	public Result cancelReleased(String id,HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		int cancelResult = 0;
		ActivityInfo temp = null;
		//判断参数
		if(id == null) {
			return Result.makeFailResult("需要删除的活动ID不能为空！");
		}
		//查询数据库是否存在该活动
		if((temp = this.activityInfoService.selectActivityById(id.trim())) != null) {
			//当活动状态为未开始时则直接删除该活动
			if(temp.getActivityFlag() == 0) {
				cancelResult = this.activityInfoService.deleteActivity(id.trim(),1);
			}
		}
		else {
			return Result.makeFailResult("不存在该活动！");
		}		
		if(cancelResult == 0) {
			return Result.makeFailResult("取消活动失败！");
		}
		//执行成功
		resultMap.put("cancelResult", cancelResult);
		return Result.makeSuccessResult(resultMap);
	}
	
	/**
	 * 更改活动信息
	 * 
	 * @param activityInfo
	 * @param request
	 * @return
	 */
	@RequestMapping("modifyActivity")
	@ResponseBody
	public Result modifyActivity(ActivityInfo activityInfo,HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		int modifyResult = 0;
		//判断活动对象是否为空
		if(activityInfo == null) {
			return Result.makeFailResult("活动对象为空！");
		}
		//查询数据库是否存在该活动
		if(activityInfo.getId() == null) {
			return Result.makeFailResult("未指定活动！");
		}
		String id = activityInfo.getId();
		//判断是否存在该活动
		if(this.activityInfoService.selectActivityById(id.trim()) == null) {
			return Result.makeFailResult("不存在该活动！");
		}
		//对各字段值进行非空判断
		if(activityInfo.getActivityTheme() == null || activityInfo.getPrincipalName() == null) {
			return Result.makeFailResult("活动名和活动主题不能为空！");
		}
		if(activityInfo.getDetailInfo() == null) {
			return Result.makeFailResult("请输入活动详细介绍！");
		}
		if(activityInfo.getOverTime() == null || activityInfo.getStartTime() == null) {
			return Result.makeFailResult("活动开始时间和结束时间不能为空！");
		}
		//执行修改数据库操作
		modifyResult = this.activityInfoService.modifyActivity(activityInfo);
		if(modifyResult == 0) {
			return Result.makeFailResult("修改活动失败！");
		}
		resultMap.put("modifyResult", modifyResult);
		return Result.makeSuccessResult(resultMap);
	}
	
	/**
	 * 获取活动列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("getActivityList")
	@ResponseBody
	public Result getActivityHistory(HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<ActivityInfo> allInfo = null;
		allInfo = this.activityInfoService.getActivityList();
		resultMap.put("activityList", allInfo);
		return Result.makeSuccessResult(resultMap);
	}
	
	/**
	 * 更改活动状态
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("modifyActivityFlag")
	@ResponseBody
	public Result modifyActivityFlag(String id,int activityFlag,HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		int modifyResult = 0;
		ActivityInfo temp = null;
		if(id == null) {
			return Result.makeFailResult("未指定活动！");
		}
		temp = this.activityInfoService.selectActivityById(id.trim());
		if(temp == null) {
			return Result.makeFailResult("不存在该活动！");
		}
		temp.setActivityFlag(activityFlag);
		modifyResult = this.activityInfoService.modifyActivity(temp);
		if(modifyResult == 0) {
			return Result.makeFailResult("修改活动状态失败！");
		}
		resultMap.put("modifyResult", modifyResult);
		return Result.makeSuccessResult(resultMap);
	}
	
	/**
	 * 获取活动状态
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("getActivityFlag")
	@ResponseBody
	public Result getActivityFlag(String id,HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		int activityFlag = 0;
		ActivityInfo temp = null;
		if(id == null) {
			return Result.makeFailResult("未指定活动！");
		}
		if((temp = this.activityInfoService.selectActivityById(id)) == null) {
			return Result.makeFailResult("不存在该活动！");
		}
		activityFlag = temp.getActivityFlag();
		resultMap.put("result", activityFlag);
		return Result.makeSuccessResult(resultMap);
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
	 
	
	
}
