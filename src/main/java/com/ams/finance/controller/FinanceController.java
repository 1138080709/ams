package com.ams.finance.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ams.finance.dao.MaterialFlowInfoDao;
import com.ams.finance.entity.CashFlowInfo;
import com.ams.finance.entity.MaterialFlowInfo;
import com.ams.finance.entity.Materials;
import com.ams.finance.service.ICashService;
import com.ams.finance.service.IMaterialsService;
import com.ams.user.entity.User;
import com.ams.utils.Result;

@Controller
@RequestMapping("finance")
public class FinanceController {
	@Resource
	private ICashService cashService;
	
	@Resource
	private IMaterialsService materialsService;
	
	/**
	 * 查询当前余额
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("queryCurrentBalance")
	@ResponseBody
	public Result queryCurrentBalance(HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
	    int balance=cashService.queryCurrentBalance();
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("balance", balance);
		return Result.makeSuccessResult(resultMap);
	}
	
	/**
	 * 资金申请 (0-支出 1-收入)
	 * 
	 * @param CashFlowApplicate
	 * @param proposerDigits
	 * @param request
	 * @return
	 */
	@RequestMapping("applicateCash")
	@ResponseBody
	public Result applicateCash(CashFlowInfo cashFlowApplicate,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		System.out.println(cashFlowApplicate.getInfoType());
		int result=cashService.applicateCash(cashFlowApplicate,currentUser.getId());
		if(result==0)
			return Result.makeFailResult("发生错误,申请失败");
		return Result.makeSuccessResult("申请成功");
	}
	
	/**
	 * 资金审核列表
	 * 
	 * @param queryInfo
	 * @param offset
	 * @param limit
	 * @param request
	 * @return
	 */
	@RequestMapping("getAuditList")
	@ResponseBody
	public Result getAuditList(String queryInfo,int offset,int limit,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		Map<String,Object> resultMap=new HashMap<String,Object>();
		List<CashFlowInfo> infoList=cashService.getAuditList(queryInfo,offset,limit);	
		int total=cashService.getCashFlowInfoCount();
		resultMap.put("infoList", infoList);
		resultMap.put("total", total);
		return Result.makeSuccessResult(resultMap);
	}
	
	/**
	 * 审核操作——批准
	 * 	
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping("authorize")
	@ResponseBody
	public Result authorize(String[] ids,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		List<String> idList=Arrays.asList(ids);
		/*
		 * for(String id:idList) System.out.println(id);
		 */
		int result1=cashService.updateApplicateAuthorizeStatus(idList,currentUser.getId());
		if(result1!=idList.size())
			return Result.makeFailResult("审核发生错误");
		return Result.makeSuccessResult("审核成功");
	}
	
	/**
	 * 审核操作——驳回
	 * 
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping("reject")
	@ResponseBody
	public Result reject(String[] ids,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		List<String> idList=Arrays.asList(ids);
		/*
		 * for(String id:idList) System.out.println(id);
		 */
		int result=cashService.updateApplicateRejectStatus(idList,currentUser.getId());
		if(result!=idList.size())
			return Result.makeFailResult("审核发生错误");
		return Result.makeSuccessResult("审核成功");
	}
	
	/**
	 * 审核操作——取消审核
	 * 
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping("cancel")
	@ResponseBody
	public Result cancel(String[] ids,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		List<String> idList=Arrays.asList(ids);
		/*
		 * for(String id:idList) System.out.println(id);
		 */
		int result=cashService.updateApplicateCancelStatus(idList,currentUser.getId());
		if(result!=idList.size())
			return Result.makeFailResult("操作发生错误");
		return Result.makeSuccessResult("操作成功");
	}
	
	/**
	 * 审批列表——审批资金
	 * 
	 * @param queryInfo
	 * @param offset
	 * @param limit
	 * @param request
	 * @return
	 */
	@RequestMapping("getExecuteList")
	@ResponseBody
	public Result getExecuteList(String queryInfo,int offset,int limit,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		Map<String,Object> resultMap=new HashMap<String,Object>();
		List<CashFlowInfo> infoList=cashService.getExecuteList(queryInfo,offset,limit);	
		int total=cashService.getExecuteCount();
		resultMap.put("infoList", infoList);
		resultMap.put("total", total);
		return Result.makeSuccessResult(resultMap);
	}
	
	/**
	 * 批资金
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("execute")
	@ResponseBody
	public Result execute(String id,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		int result=cashService.updateCashCount(id);
		if(result==0)
			return Result.makeFailResult("余额修改发生错误,请重试");
		result=cashService.updateExecuteFlag(id);
		if(result==0)
			return Result.makeFailResult("数据库操作出现问题");
		return Result.makeSuccessResult("操作成功");
	}
	
	/**
	 * 资金撤回
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("cancelExecute")
	@ResponseBody
	public Result cancelExecute(String id,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		int result=cashService.cancelCashCount(id);
		if(result==0)
			return Result.makeFailResult("余额修改发生错误,请重试");
		result=cashService.cancelExecuteFlag(id);
		if(result==0)
			return Result.makeFailResult("数据库操作出现问题");
		return Result.makeSuccessResult("操作成功");
	}
	
	/**
	 * 资金流动列表
	 * 
	 * @param queryInfo
	 * @param offset
	 * @param limit
	 * @param request
	 * @return
	 */
	@RequestMapping("getCashFlowInfoList")
	@ResponseBody
	public Result getCashFlowInfoList(String queryInfo,int offset,int limit,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		Map<String,Object> resultMap=new HashMap<String,Object>();
		List<CashFlowInfo> infoList=cashService.getCashFlowInfoList(queryInfo,offset,limit);	
		int total=cashService.getCashFlowInfoListCount();
		resultMap.put("infoList", infoList);
		resultMap.put("total", total);
		return Result.makeSuccessResult(resultMap);
	}
	
	/**
	 * 个人审核历史
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("personalAuditHistory")
	@ResponseBody
	public Result personalAuditHistory(String queryInfo,int offset,int limit,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		Map<String,Object> resultMap=new HashMap<String,Object>();
		List<CashFlowInfo> infoList=cashService.getpersonalAuditHistory(currentUser.getId(),queryInfo,offset,limit);
		int total=cashService.getpersonalAuditHistoryCount(currentUser.getId());
		resultMap.put("infoList", infoList);
		resultMap.put("total", total);
		return Result.makeSuccessResult(resultMap);
		
	}
	
	/**
	 * 登记物资接口
	 * 
	 * @param materials
	 * @param request
	 * @return
	 */
	@RequestMapping("registerMaterial")
	@ResponseBody
	public Result registerMaterial(Materials materials,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		int result=materialsService.saveMaterialInfo(materials);
		if(result==0)
			return Result.makeFailResult("登记失败，请重试");
		return Result.makeSuccessResult("登记成功");
	}
	
	/**
	 * 更改物资信息接口
	 * 
	 * @param materials
	 * @param request
	 * @return
	 */
	@RequestMapping("updateMaterial")
	@ResponseBody
	public Result updateMaterial(Materials materials,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		Materials orderMaterial=materialsService.getMaterialsById(materials.getId());
		if(orderMaterial==null)
			return Result.makeFailResult("找不到当前的物资信息");
		int result=materialsService.updateMaterialsInfo(materials);
		if(result==0)
			return Result.makeFailResult("信息修改失败");
		else
			return Result.makeSuccessResult("信息修改成功");
	}
	
	/**
	 * 物资丢失接口
	 * @param request
	 * @return
	 */
	@RequestMapping("loseMaterial")
	@ResponseBody
	public Result loseMaterial(String[] ids,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		List<String> idList=Arrays.asList(ids);
		int result=materialsService.updateMaterialsDelFlag(idList);
		if(result==0)
			return Result.makeFailResult("删除过程发生错误");
		return Result.makeSuccessResult("删除成功");
	}
	
	/**
	 * 物资借还登记接口
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("lendAndReturnMaterial")
	@ResponseBody
	public Result lendAndReturnMaterial(MaterialFlowInfo materialFlowInfo,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		int result=materialsService.addMaterialFlowInfo(materialFlowInfo,currentUser.getId());
		if(result==0)
			Result.makeFailResult("登记过程发生错误，请重新登记");
		result=materialsService.updateMaterialNumber(materialFlowInfo);
		if(result==-1)
			return Result.makeFailResult("物资数量不足");
		else if(result==0)
			return Result.makeFailResult("登记发生错误，请重试");
		else
			return Result.makeSuccessResult("登记成功");
	}
	
	
//	/**
//	 * 查看当前物资情况
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("showMaterial")
//	@ResponseBody
//	public Result showMaterial(String queryInfo,int offset,int limit,HttpServletRequest request) {
//		HttpSession session=request.getSession();
//		User currentUser=(User)session.getAttribute("currentUser");
//		if(currentUser==null) 
//			return Result.makeFailResult("用户登录已失效,请重新登录");
//		Map<String,Object> resultMap=new HashMap<String,Object>();
//		List<Materials> infoList=materialsService.getMaterialsList(queryInfo,offset,limit);
//		int total=materialsService.getMaterialsCount();
//		resultMap.put("infoList", infoList);
//		resultMap.put("total", total);
//		return Result.makeSuccessResult(resultMap);
//	}
	
	/**
	 * 查看当前物资情况
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("showMaterial")
	@ResponseBody
	public Result showMaterial(String queryInfo,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		Map<String,Object> resultMap=new HashMap<String,Object>();
		List<Materials> infoList=materialsService.getMaterialsList(queryInfo);
		int total=materialsService.getMaterialsCount();
		resultMap.put("infoList", infoList);
		resultMap.put("total", total);
		return Result.makeSuccessResult(resultMap);
	}
	
//	/**
//	 * 查看借还信息记录
//	 * 
//	 * @param queryInfo
//	 * @param offset
//	 * @param limit
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("checkLoanStatus")
//	@ResponseBody
//	public Result checkLoanStatus(String queryInfo,int offset,int limit,HttpServletRequest request) {
//		HttpSession session=request.getSession();
//		User currentUser=(User)session.getAttribute("currentUser");
//		if(currentUser==null) 
//			return Result.makeFailResult("用户登录已失效,请重新登录");
//		Map<String,Object> resultMap=new HashMap<String,Object>();
//		List<MaterialFlowInfoDao> infoList=materialsService.getMaterialFlowInfoList(queryInfo,offset,limit);
//		int total=materialsService.getMaterialsFlowInfoCount();
//		resultMap.put("infoList", infoList);
//		resultMap.put("total", total);
//		return Result.makeSuccessResult(resultMap);
//	}
	
	/**
	 * 查看借还信息记录
	 * 
	 * @param queryInfo
	 * @param request
	 * @return
	 */
	@RequestMapping("checkLoanStatus")
	@ResponseBody
	public Result checkLoanStatus(String queryInfo,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		Map<String,Object> resultMap=new HashMap<String,Object>();
		List<MaterialFlowInfoDao> infoList=materialsService.getMaterialFlowInfoList(queryInfo);
		int total=materialsService.getMaterialsFlowInfoCount();
		resultMap.put("infoList", infoList);
		resultMap.put("total", total);
		return Result.makeSuccessResult(resultMap);
	}
}
