package com.ams.user.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
import com.ams.department.entity.Department;
import com.ams.department.entity.Job;
import com.ams.department.service.IDepartmentService;
import com.ams.department.service.IJobService;
import com.ams.user.entity.MemberApplicateInfo;
import com.ams.user.entity.User;
import com.ams.user.service.IMemberApplicateInfoService;
import com.ams.user.service.IUserService;
import com.ams.utils.DESUtils;
import com.ams.utils.IdGen;
import com.ams.utils.Page;
import com.ams.utils.Result;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private IUserService userService;
	@Resource
	private IMemberApplicateInfoService memberApplicateInfoService;
	@Resource
	private IDepartmentService departmentService;
	@Resource
	private IJobService jobService;
	
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
	 * 表单申请数据保存接口
	 * 
	 * @param memberApplicateInfo
	 * @param departmentName
	 * @param jobName
	 * @param request
	 * @return
	 */
	@RequestMapping("saveMemberApplicationInfo")
	@ResponseBody
	public Result saveMemberApplicationInfo(MemberApplicateInfo memberApplicateInfo,String departmentName,String jobName,HttpServletRequest request) {
		Department department=departmentService.selectDepartmentByName(departmentName);
		if(department==null)
			return Result.makeFailResult("查无该部门，请确认后重新输入");
		Job job=jobService.getJobByName(jobName);
		if(job==null)
			return Result.makeFailResult("查无该职位，请确认后重新输入");
		MemberApplicateInfo memberApplicate=memberApplicateInfoService.getMemberApplicateInfoByDigits(memberApplicateInfo.getDigits());
		if(memberApplicate!=null)
			return Result.makeFailResult("学号已申请,有问题请联系管理员");
		int result=memberApplicateInfoService.addInfoByMemberApplicateInfo(memberApplicateInfo,department,job);
		if(result==0)
			return Result.makeFailResult("申请失败");
		else
			return Result.makeSuccessResult("申请成功");
	}
	
	/**
	 * 获得申请记录接口
	 * 
	 * @param queryInfo
	 * @param offset
	 * @param limit
	 * @param request
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
	 * 更新审核状态_通过
	 * 
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping("updateApplicationSuccessStatus")
	@ResponseBody
	public Result updateApplicationSuccessStatus(String ids,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		List<String> idList=Arrays.asList(ids);
		int result=memberApplicateInfoService.updateApplicateSuccessStatus(idList);
		if(result==0) 
			return Result.makeFailResult("审核失败");
		else {
			return Result.makeFailResult("审核成功");
		}
	}
	
	/**
	 * 更新审核状态_失败
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("updateApplicationFailStatus")
	@ResponseBody
	public Result updateApplicationFailStatus(String[] ids,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		List<String> idList=Arrays.asList(ids);
	/**	for(int i=0;i<id.length;i++)
			System.out.println(id[i]);
		for(String d:idList)
			System.out.println(d);**/
		int result=memberApplicateInfoService.updateApplicateFailStatus(idList);
		if(result==0) 
			return Result.makeFailResult("审核失败");
		else 
			return Result.makeSuccessResult("审核成功");
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
	
	/**
	 * 删除用户(只需将User对象的DelFlag字段设为1表示已删除即可)
	 * 
	 * @param digits
	 * @param request
	 * @return
	 */
	@RequestMapping("deleteUser")
	@ResponseBody
	public Result deleteUserByDigits(String digits,HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		User user = null;
		//判断学号是否为空
		if(digits == null) {
			return Result.makeFailResult("请输入学号!");
		}
		//判断是否有该用户
		user = this.userService.getUserByDigits(digits.trim());
		if(user == null) {
			return Result.makeFailResult("无该用户!");
		}
		//更新数据库
		int delResult = this.userService.updateDelFlagByDigits(digits,1);
		//更新失败
		if(delResult == 0) {
			return Result.makeFailResult("删除用户失败!");
		}
		//更新成功
		resultMap.put("delResult", user.getDelFlag());
		return Result.makeSuccessResult(resultMap);
	}
	
	/**
	 * 添加用户信息(与批量导入用户区分，此功能为单个添加用户)
	 * 
	 * @param name
	 * @param digits
	 * @param request
	 * @return
	 */
	@RequestMapping("addSingleUser")
	@ResponseBody
	public Result addSingleUser(String name,String digits,HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int addResult = 0;
		User user = null;
		//验证姓名及学号是否为空	
		if(name == null || digits == null) { 
			return Result.makeFailResult("用户姓名及学号是必填项!"); 
		}
		//验证数据库中是否已存在该用户
		user = this.userService.getUserByDigits(digits.trim());
		if(user != null) {
			return Result.makeFailResult("已存在该学号的用户!");
		}
		//若以上验证均通过则更新数据库
		else {
			user = new User();
			String id=IdGen.uuid();
			user.setId(id);
			user.setName(name);
			user.setDigits(digits);
			user.setCreateTime(date.format(new Date()));
			user.setRoleFlag(5);			
		}
		addResult = this.userService.addSingleUser(user);
		//判断添加语句是否成功执行
		if(addResult == 0) { 
			return Result.makeFailResult("添加用户失败!"); 
		}
		//添加语句执行成功
		resultMap.put("addResult", addResult);
		return Result.makeSuccessResult(resultMap);		 
	}
	
	/**
	 * 修改用户基本信息(根据传入的参数可修改单个用户信息或多个)
	 * 
	 * @param digits
	 * @param name
	 * @param department
	 * @param major
	 * @param belongClass
	 * @param grade
	 * @param phone
	 * @param email
	 * @param request
	 * @return
	 */
	@RequestMapping("updateUserInfoByDigits")
	@ResponseBody
	public Result updateUserInfoByDigits(String digits,String name,String department,String major,String belongClass,String grade,String phone,String email) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		int updateResult = 0;
		User user = null;
		//验证是否输入学号
		if(digits == null) {
			return Result.makeFailResult("学号项是必填项!");
		}
		//验证是否存在该用户
		user = this.userService.getUserByDigits(digits.trim());
		if(user == null) {
			return Result.makeFailResult("无该学号的用户!");
		}
		//通过以上验证则更新数据库
		updateResult = this.userService.updateUserInfoByDigits(digits, name, department, major, belongClass, grade, phone, email, user);
		//验证数据库是否更新成功
		if(updateResult == 0) {
			return Result.makeFailResult("更新失败!");
		}
		//返回更新结果
		resultMap.put("updateResult", updateResult);
		return Result.makeSuccessResult(resultMap);
	}
	
	/**
	 * 用户分页列表
	 * 
	 * @param pageNow
	 * @param request 
	 * @return
	 */
	@RequestMapping("showUsersByPage")
	@ResponseBody
	public Result showUsersByPage(String pageNow,HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//加载Page工具
		Page page = null;
		//用来装载当前用户列表
		List<User> allUsers = null;
		//获取当前users表中所有用户的数量
		int totalCount = this.userService.getUsersCount();
		//验证是否有用户
		if(totalCount == 0) {
			return Result.makeFailResult("无用户!");
		}
		//默认为第1页
		page = new Page(totalCount,1);
		if(pageNow != null) {
			int pageNum = Integer.parseInt(pageNow.trim());
			int totalPageNum = page.getTotalPageCount();
			//验证页码数是否在合法范围内
			if(pageNum <= 0 || pageNum > totalPageNum) {
				return Result.makeFailResult("请输入正确页码!");
			}
			//页码数在合法范围内
			else {
				page.setPageNow(pageNum);
				allUsers = this.userService.getUsersByPage(page.getStartPos(), page.getPageSize());
			}
		}
		//未指定页码默认为1
		else {
			allUsers = this.userService.getUsersByPage(page.getStartPos(), page.getPageSize());
		}
		resultMap.put("page", page);
		resultMap.put("usersList", allUsers);
		return Result.makeSuccessResult(resultMap);
	}
}
