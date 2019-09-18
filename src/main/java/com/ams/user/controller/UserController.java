package com.ams.user.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ams.department.dto.QuerySaveDepartmentInfoDTO;
import com.ams.user.entity.MemberApplicateInfo;
import com.ams.user.entity.User;
import com.ams.user.service.IMemberApplicateInfoService;
import com.ams.user.service.IUserService;
import com.ams.utils.DESUtils;
import com.ams.utils.Result;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private IUserService userService;
	@Resource
	private IMemberApplicateInfoService memberApplicateInfoService;
	
	@RequestMapping("index")
	public String toIndex() {
		return "login";
	}
	/**
	 * 登录接口
	 * @param digits
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping("login")
	@ResponseBody
	public Result login(String digits,String password,HttpServletRequest request) {
		Map<String,Object> resultMap=new HashMap<String,Object>();
		User user=null;
		HttpSession session=request.getSession();
		//System.out.println("学号:"+digits+" "+"密码："+password);
		if(password==null||digits==null) 
			return Result.makeFailResult("密码或用户名为空");
		String encryptPassword=DESUtils.getEncryptString(password.trim());
		user=this.userService.getUserByDigits(digits.trim());
		if(user==null||!user.getPassword().equals(encryptPassword)) 
			return Result.makeFailResult("用户名或密码错误");
		session.setAttribute("currentUser", user);//将登录的用户放进session,方便后面使用
		resultMap.put("roleFlag",user.getRoleFlag());
		return Result.makeSuccessResult(resultMap);
	}
	/**
	 * 密码重置
	 *  
	 * @param digits
	 * @param request
	 * @return
	 */
	@RequestMapping("resetpassword")
	@ResponseBody
	public Result resetPassword(String digits,HttpServletRequest request) {
		String newPassword="000000";
		String encryptPassword=DESUtils.getEncryptString(newPassword.trim());
		System.out.println("学号:"+digits+"密码:"+encryptPassword);
		int result=userService.updatePasswordByDigits(digits,encryptPassword);
		if(result==0) 
			return Result.makeFailResult("密码重置失败");
		else 
			return Result.makeSuccessResult("密码重置成功");
	}
	/**
	 * 修改密码
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @param request
	 * @return
	 */
	@RequestMapping("setpassword")
	@ResponseBody
	public Result setPassword(String oldPassword,String newPassword,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		System.out.println("当前用户的学号"+currentUser.getDigits());
		if(!DESUtils.getEncryptString(oldPassword).equals(currentUser.getPassword())) 
			return Result.makeFailResult("旧密码错误");
		int result=userService.updatePasswordByDigits(currentUser.getDigits(), DESUtils.getEncryptString(newPassword));
		if(result==0) 
			return Result.makeFailResult("密码修改失败");
		else 
			return Result.makeSuccessResult("密码修改成功");
	}
	
	/**
	 * 表单申请数据保存接口(未测试)
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	public String saveMemberApplicationInfo(HttpServletRequest request,Model model) {
		
		return "";
	}
	
	/**
	 * 获得申请记录接口(未测试)
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getMemberApplicationInfo")
	@ResponseBody
	public Result getMemberApplicationInfo(String queryInfo,int offset,int limit,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<MemberApplicateInfo> applicateInfo = null;
		applicateInfo = memberApplicateInfoService.getAllApplicateInfoList(queryInfo, offset, limit); 
		int total = 0;
		total = memberApplicateInfoService.getAllApplicateCount();
		/*测试代码
		int i=1;
		for(QuerySaveDepartmentInfoDTO department:departments) {
			System.out.println(i+":"+department.getDepartmentName());
		}
		System.out.println("总数为:"+total);
		*/
		resultMap.put("applicateInfo", applicateInfo);
		resultMap.put("total", total);
		return Result.makeSuccessResult(resultMap);
	}
	
	/**
	 * 更新审核状态_通过(未测试)
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("updateApplicationSuccessStatus")
	@ResponseBody
	public Result updateApplicationSuccessStatus(HttpServletRequest request,@RequestParam List<String> idList) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		if(idList==null) 
			return Result.makeFailResult("请选中你的审核对象");
		int result=memberApplicateInfoService.updateApplicateSuccessStatus(idList);
		if(result==0) 
			return Result.makeFailResult("审核失败");
		else 
			return Result.makeFailResult("审核成功");
	}
	
	/**
	 * 更新审核状态_失败（未测试）
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("updateApplicationFailStatus")
	@ResponseBody
	public Result updateApplicationFailStatus(HttpServletRequest request,@RequestParam List<String> idList) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		if(idList==null) 
			return Result.makeFailResult("请选中你的审核对象");
		int result=memberApplicateInfoService.updateApplicateFailStatus(idList);
		if(result==0) 
			return Result.makeFailResult("审核失败");
		else 
			return Result.makeFailResult("审核成功");
	}
	
	/**
	 * 批量导入接口(未测试)
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("imports")
	@ResponseBody
	public Result imports(HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		try {
		//获取上次的文件
		MultipartHttpServletRequest multipart=(MultipartHttpServletRequest)request;
		MultipartFile file=multipart.getFile("upfile");
		InputStream in=file.getInputStream();
		//数据导入
		userService.importExcelInfo(in,file);
		in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Result.makeSuccessResult();
	}
	
	/**
	 * 批量导入接口(未测试)
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("exports")
	@ResponseBody
	public void exports(HttpServletRequest request,HttpServletResponse response) {
		response.reset();
		//指定下载的文件名，浏览器都会使用本地编码，即GBK，浏览器收到这个文件名后，用ISO-8859-1来解码，然后用GBK来显示
		//所以我们用GBK解码，用ISO-8859-1来编码，在浏览器那边会返过来执行
		//response.setHeader("Content-Disposition","attachment;filename="+new String(userDate.getBytes("GBK"),"ISO-8859-1"));
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires",0);
		XSSFWorkbook workbook=null;
		//导出Excel对象
		workbook=userService.exportExcelInfo();
		OutputStream output;
		try {
			output=response.getOutputStream();
			BufferedOutputStream bufferedOutput=new BufferedOutputStream(output);
			bufferedOutput.flush();
			workbook.write(bufferedOutput);
			bufferedOutput.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
