package com.ams.activity.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ams.activity.entity.ActivityInfo;
import com.ams.activity.entity.ActivityParticipatorInfo;
import com.ams.activity.service.IActivityInfoService;
import com.ams.activity.service.IActivityParticipatorInfoService;
import com.ams.user.entity.User;
import com.ams.utils.IdGen;
import com.ams.utils.KMP;
import com.ams.utils.Result;

@Controller
@RequestMapping("/activity")
public class ActivityController {
	@Resource
	private IActivityInfoService activityInfoService;
	@Resource
	private IActivityParticipatorInfoService activityParticipatorInfoService;
	
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
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
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
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
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
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
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
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
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
	 * 搜索活动详细信息
	 * 
	 * @param words
	 * @param request
	 * @return
	 */
	@RequestMapping("queryActivityDetail")
	@ResponseBody
	public Result queryActivityDetail(char[] words,HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<ActivityInfo> target = new ArrayList<ActivityInfo>();
		List<ActivityInfo> select = null;
		KMP match = null;		
		char[] detail = null;
		select = this.activityInfoService.getActivityList();
		for(ActivityInfo actInfo:select) {
			detail = actInfo.getDetailInfo().toCharArray();
			match = new KMP(detail,words);
			if(match.kmpMatch() != -1) {
				target.add(actInfo);
			}
		}
		resultMap.put("targetList", target);
		return Result.makeSuccessResult(resultMap);
	}
	
	/**
	 * 活动报名接口
	 * @param activityParticipatorInfo
	 * @param request
	 * @return
	 */
	@RequestMapping("applyActivity")
	@ResponseBody
	public Result applyActivity(String Id,ActivityParticipatorInfo activityParticipatorInfo,HttpServletRequest request) {
		String digits=activityParticipatorInfo.getParticipatorDigits();
		String activityId=Id;
		activityParticipatorInfo.setActivitId(Id);
//		System.out.println("学号:"+digits);
//		System.out.println("活动:"+activityId);
//		System.out.println("学院:"+activityParticipatorInfo.getParticipatorDepartment());
//		System.out.println("班级:"+activityParticipatorInfo.getParticipatorClass());
//		System.out.println("姓名:"+activityParticipatorInfo.getParticipatorName());
		if(digits==null||digits.equals(""))
			return Result.makeFailResult("学号/工号不能为空");
		if(activityId==null||activityId.equals(""))
			return Result.makeFailResult("活动Id不能为空");
		ActivityInfo activity=activityInfoService.selectActivityById(activityId);
		if(activity==null)
			return Result.makeFailResult("该活动不存在");
		if(activity.getActivityFlag()==0||activity.getActivityFlag()==2)
			return Result.makeFailResult("活动未开始或已结束");
		int result=activityParticipatorInfoService.applyActivity(activityParticipatorInfo);
		if(result==-1)
			return Result.makeFailResult("无法重复报名");
		if(result==0)
			return Result.makeFailResult("报名发生错误");
		else
			return Result.makeSuccessResult();
	}
	 
	/**
	 * 获取活动报名列表
	 * @param Id
	 * @param request
	 * @return
	 */
	@RequestMapping("getApplyList")
	@ResponseBody
	public Result getApplyList(String Id,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		if(Id==null||Id.equals(""))
			return Result.makeFailResult("活动Id不能为空");
		ActivityInfo activity=activityInfoService.selectActivityById(Id);
		if(activity==null)
			return Result.makeFailResult("该活动不存在");
		List<ActivityParticipatorInfo> applyList=activityParticipatorInfoService.getApplyListById(Id);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("applyList",applyList);
		return Result.makeSuccessResult(resultMap);
	}
	
	/**
	 * 获得历史活动
	 * @param request
	 * @return
	 */
	@RequestMapping("getHistoryList")
	@ResponseBody
	public Result getHistoryList(HttpServletRequest request) {
		List<ActivityInfo> historyList=activityInfoService.getHistoryList();
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("historyList",historyList);
		return Result.makeSuccessResult(resultMap);
	}
	
}
