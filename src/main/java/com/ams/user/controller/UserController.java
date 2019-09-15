package com.ams.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ams.user.entity.User;
import com.ams.user.service.IUserService;
import com.ams.utils.DESUtils;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private IUserService userService;
	@RequestMapping("index")
	public String toIndex() {
		return "login";
	}
	/**
	 * 登录接口
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("login")
	public String login(HttpServletRequest request,Model model) {
		User user=null;
		HttpSession session=request.getSession();
		String digits=request.getParameter("digits");
		String password=request.getParameter("password");
		//System.out.println("学号:"+digits+" "+"密码："+password);
		if(password==null||digits==null) {
			model.addAttribute("success", null);
			model.addAttribute("error", "用户名或密码为空");
			return "login";
		}
		String encryptPassword=DESUtils.getEncryptString(password.trim());
		user=this.userService.getUserByDigits(digits.trim());
		if(user==null||!user.getPassword().equals(encryptPassword)) {
			model.addAttribute("success", null);
			model.addAttribute("error","用户名或密码错误");
			return "login";
		}
		session.setAttribute("currentUser", user);//将登录的用户放进session,方便后面使用
		if(user.getRoleFlag()==0) {
			model.addAttribute("error", null);
			model.addAttribute("success","系统管理员用户登录成功");
			return "SystemManager";
		}else if(user.getRoleFlag()==1) {
			model.addAttribute("error", null);
			model.addAttribute("success","一级管理员用户登录成功");
			return "FirstManager";
		}else if(user.getRoleFlag()==2) {
			model.addAttribute("error", null);
			model.addAttribute("success","二级管理员用户登录成功");
			return "SecondManager";
		}else if(user.getRoleFlag()==3) {
			model.addAttribute("error", null);
			model.addAttribute("success","三级管理员用户登录成功");
			return "ThirdManager";
		}else if(user.getRoleFlag()==4) {
			model.addAttribute("error", null);
			model.addAttribute("success","四级管理员用户登录成功");
			return "FourthManager";
		}else {
			model.addAttribute("error", null);
			model.addAttribute("success","五级管理员用户登录成功");
			return "FifthManager";
		}
	}
	/**
	 * 密码重置
	 * @param digits
	 * @param model
	 * @return
	 */
	@RequestMapping("resetpassword")
	public String resetPassword(HttpServletRequest request,Model model) {
		String digits=request.getParameter("digits");
		String newPassword="000000";
		String encryptPassword=DESUtils.getEncryptString(newPassword.trim());
		System.out.println("学号:"+digits+"密码:"+encryptPassword);
		int result=userService.updatePasswordByDigits(digits,encryptPassword);
		if(result==0) {
			model.addAttribute("success", null);
			model.addAttribute("error", "密码重置失败");
		}
		else {
			model.addAttribute("error", null);
			model.addAttribute("success", "密码重置成功");
		}
		return "SystemManager";
	}
	/**
	 * 修改密码
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("setpassword")
	public String setPassword(HttpServletRequest request, Model model) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) {
			model.addAttribute("success", null);
			model.addAttribute("error", "用户登录已失效,请重新登录");
			return "login";
		}
		System.out.println("当前用户的学号"+currentUser.getDigits());
		String oldPassword=request.getParameter("oldPassword");
		String newPassword=request.getParameter("newPassword");
		if(!DESUtils.getEncryptString(oldPassword).equals(currentUser.getPassword())) {
			model.addAttribute("success", null);
			model.addAttribute("error", "旧密码错误");
			if(currentUser.getRoleFlag()==0) 
				return "SystemManager";
			else if(currentUser.getRoleFlag()==1) 
				return "FirstManager";
			else if(currentUser.getRoleFlag()==2) 
				return "SecondManager";
			else if(currentUser.getRoleFlag()==3) 
				return "ThirdManager";
			else if(currentUser.getRoleFlag()==4) 
				return "FourthManager";
			else 
				return "FifthManager";
		}
		int result=userService.updatePasswordByDigits(currentUser.getDigits(), DESUtils.getEncryptString(newPassword));
		if(result==0) {
			model.addAttribute("success", null);
			model.addAttribute("error", "密码修改失败");
		}
		else {
			model.addAttribute("error", null);
			model.addAttribute("success", "密码修改成功");
		}
		if(currentUser.getRoleFlag()==0) 
			return "SystemManager";
		else if(currentUser.getRoleFlag()==1) 
			return "FirstManager";
		else if(currentUser.getRoleFlag()==2)
			return "SecondManager";
		else if(currentUser.getRoleFlag()==3) 
			return "ThirdManager";
		else if(currentUser.getRoleFlag()==4) 
			return "FourthManager";
		else 
			return "FifthManager";
		
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
	public String getMemberApplicationInfo(HttpServletRequest request,Model model) {
		return "";
	}
	
	/**
	 * 更新审核状态
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	public String updateApplicationStatus(HttpServletRequest request,Model model) {
		return "";
	}
}
