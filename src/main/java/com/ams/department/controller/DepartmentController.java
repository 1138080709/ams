package com.ams.department.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ams.department.dto.QuerySaveDepartmentInfoDTO;
import com.ams.department.dto.QuerySaveJobInfoDTO;
import com.ams.department.dto.QuerySaveMemberInfoDTO;
import com.ams.department.entity.Department;
import com.ams.department.entity.Job;
import com.ams.department.service.IDepartmentService;
import com.ams.department.service.IJobService;
import com.ams.user.dao.UserDao;
import com.ams.user.entity.User;
import com.ams.user.service.IUserService;
import com.ams.user.service.impl.UserServiceImpl;
import com.ams.utils.IdGen;

/**
 * 
 * @author 老吴
 * @create 2019-09-06
 */
@Controller
@RequestMapping("department")
public class DepartmentController {
	@Resource
	private IDepartmentService departmentService;
	@Resource
	private IUserService userService;
	@Resource
	private IJobService jobService;
	/**
	 * 实现部门列表分页查询 返回DTO类(包含部门与部长的基本信息)
	 * 测试问题在于，如何将offset和limit的数据从前端传进后端
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getDepartmentList")
	public String getDepartmentList(HttpServletRequest request, Model model) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) {
			if(currentUser==null) {
				model.addAttribute("success", null);
				model.addAttribute("error", "用户登录已失效,请重新登录");
				return "login";
			}
		}
		String queryInfo = request.getParameter("queryInfo");//查询信息
		int offset = Integer.parseInt(request.getParameter("offset"));//初始的
		int limit = Integer.parseInt(request.getParameter("limit"));//限制每页有多个项目
		Map<Object, Object> resultMap = new HashMap<Object, Object>();
		List<QuerySaveDepartmentInfoDTO> departments = null;
		departments = departmentService.getAlldepartmentList(queryInfo, offset, limit); 
		int total = 0;
		total = departmentService.getAlldepartmentCount();
		/*测试代码
		int i=1;
		for(QuerySaveDepartmentInfoDTO department:departments) {
			System.out.println(i+":"+department.getDepartmentName());
		}
		System.out.println("总数为:"+total);
		*/
		resultMap.put("departments", departments);
		resultMap.put("total", total);
		model.addAttribute("departmentUserList", resultMap);
		if(currentUser.getRoleFlag()==1)
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
	 * 更改部门信息接口
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("updateDepartmentInfo")
	public String UpdateDepartmentInfo(HttpServletRequest request, Model model) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		String departmentId=request.getParameter("departmentId");
		String newDepartmentName=request.getParameter("newDepartmentName");
		String newMinisterDigits=request.getParameter("newMinisterDigits");
		User ministerUser=userService.getUserByDigits(newMinisterDigits);
		if(currentUser==null) {
			if(currentUser==null) {
				model.addAttribute("success", null);
				model.addAttribute("error", "用户登录已失效,请重新登录");
				return "login";
			}
		}
		if(ministerUser==null) {
			model.addAttribute("success", null);
			model.addAttribute("error","找不到该成员");
			if(currentUser.getRoleFlag()==1) 
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
		String newMinisterId=ministerUser.getId();
		String newDescribe=request.getParameter("newDescribe");
		int result=departmentService.updateInfoById(departmentId,newDepartmentName,newMinisterId,newDescribe);
		if(result==0) {
			model.addAttribute("success", null);
			model.addAttribute("error", "信息修改失败");
			if(currentUser.getRoleFlag()==1) 
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
		else {
			model.addAttribute("error", null);
			model.addAttribute("success", "信息修改成功");
			if(currentUser.getRoleFlag()==1) 
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
	}
	/**
	 * 添加新部门接口
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("addDepartment")
	public String addDepartment(HttpServletRequest request,Model model) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) {
			if(currentUser==null) {
				model.addAttribute("success", null);
				model.addAttribute("error", "用户登录已失效,请重新登录");
				return "login";
			}
		}
		String departmentId=IdGen.uuid();
		String departmentName=request.getParameter("newDepartmentName");
		if(departmentName==null||departmentName=="") {
			model.addAttribute("success", null);
			model.addAttribute("error", "部门名不能为空");
			if(currentUser.getRoleFlag()==1)
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
		String ministerId=request.getParameter("newMinisterId");
		String describe=request.getParameter("newDescribe");
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime=df.format(new Date());
		Department newDepartment=new Department(departmentId,departmentName,ministerId,describe,createTime);
		int result=departmentService.insertNewDepartment(newDepartment);
		if(result==0) {
			model.addAttribute("success", null);
			model.addAttribute("error", "部门创建出错");
			if(currentUser.getRoleFlag()==1)
				return "FirstManager";
			else if(currentUser.getRoleFlag()==2)
				return "SecondManager";
			else if(currentUser.getRoleFlag()==3)
				return "ThirdManager";
			else if(currentUser.getRoleFlag()==4)
				return "FourthManager";
			else 
				return "FifthManager";
		}else {
			model.addAttribute("error", null);
			model.addAttribute("success", "部门创建成功");
			if(currentUser.getRoleFlag()==1)
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
		
	}
	
	/**
	 * 删除部门接口
	 * 注意部门成员的级联处理
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("delDepartment")
	public String delDepartment(HttpServletRequest request,Model model) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) {
			if(currentUser==null) {
				model.addAttribute("success", null);
				model.addAttribute("error", "用户登录已失效,请重新登录");
				return "login";
			}
		}
		String selectDepartmentId=request.getParameter("selectDepartmentId");
		//System.out.println(selectDepartmentId);
		int result=jobService.getJobCountByDepartmentId(selectDepartmentId);
		if(result!=0) {
			model.addAttribute("success", null);
			model.addAttribute("error", "该部门还有剩余职位,删除部门失败");
			if(currentUser.getRoleFlag()==1)
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
		result=departmentService.updateDelFlagById(selectDepartmentId);
		//System.out.println(result);
		if(result==0) {
			model.addAttribute("success", null);
			model.addAttribute("error", "部门删除失败");
			if(currentUser.getRoleFlag()==1)
				return "FirstManager";
			else if(currentUser.getRoleFlag()==2)
				return "SecondManager";
			else if(currentUser.getRoleFlag()==3)
				return "ThirdManager";
			else if(currentUser.getRoleFlag()==4)
				return "FourthManager";
			else 
				return "FifthManager";
		}else {
			model.addAttribute("error", null);
			model.addAttribute("success", "部门删除成功");
			if(currentUser.getRoleFlag()==1)
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
	}
	
	/**
	 * 实现职位列表分页查询 返回DTO类(包含职位与所属部门的基本信息)
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getJobList")
	public String getJobList(HttpServletRequest request,Model model){
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) {
			if(currentUser==null) {
				model.addAttribute("success", null);
				model.addAttribute("error", "用户登录已失效,请重新登录");
				return "login";
			}
		}
		String queryInfo = request.getParameter("queryInfo");
		int offset = Integer.parseInt(request.getParameter("offset"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		Map<Object, Object> resultMap = new HashMap<Object, Object>();
		List<QuerySaveJobInfoDTO> jobs = null;
		jobs = jobService.getAllJobList(queryInfo, offset, limit); 
		int total = 0;
		total = jobService.getAllJobCount();
		resultMap.put("jobs", jobs);
		resultMap.put("total", total);
		model.addAttribute("jobsList", resultMap);
		if(currentUser.getRoleFlag()==1)
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
	 * 创建职业接口
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("addJob")
	public String addJob(HttpServletRequest request,Model model) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) {
			if(currentUser==null) {
				model.addAttribute("success", null);
				model.addAttribute("error", "用户登录已失效,请重新登录");
				return "login";
			}
		}
		String jobId=IdGen.uuid();
		String jobName=request.getParameter("jobName");//不能为空
		if(jobName==null||jobName=="") {
			model.addAttribute("success", null);
			model.addAttribute("error", "职业名不能为空");
			if(currentUser.getRoleFlag()==1)
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
		int roleFlag=Integer.parseInt(request.getParameter("roleFlag"));//不能为空
		if(roleFlag==0) {
			model.addAttribute("success", null);
			model.addAttribute("error", "职业权限不能为空");
			if(currentUser.getRoleFlag()==1)
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
		String belongId=request.getParameter("departmentId");
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime=df.format(new Date());
		Job job=new Job(jobId,jobName,roleFlag,belongId,createTime);
		int Result=jobService.insertNewJob(job);
		if(Result==0) {
			model.addAttribute("success", null);
			model.addAttribute("error", "职业创建失败");
			if(currentUser.getRoleFlag()==1)
				return "FirstManager";
			else if(currentUser.getRoleFlag()==2)
				return "SecondManager";
			else if(currentUser.getRoleFlag()==3)
				return "ThirdManager";
			else if(currentUser.getRoleFlag()==4)
				return "FourthManager";
			else 
				return "FifthManager";
		}else {
			model.addAttribute("error", null);
			model.addAttribute("success", "职业创建成功");
			if(currentUser.getRoleFlag()==1)
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
	}
	/**
	 * 修改职业信息接口
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("updateJob")
	public String updateJob(HttpServletRequest request,Model model) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) {
			if(currentUser==null) {
				model.addAttribute("success", null);
				model.addAttribute("error", "用户登录已失效,请重新登录");
				return "login";
			}
		}
		String orderJobId=request.getParameter("jobId");
		Job orderJob=jobService.getJobById(orderJobId);
		if(orderJobId==null||orderJob==null){
			model.addAttribute("success", null);
			model.addAttribute("error", "找不到当前的职业信息");
			if(currentUser.getRoleFlag()==1)
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
		String newJobName=request.getParameter("newJobName");
		int newRoleFlag=Integer.parseInt(request.getParameter("newRoleFlag"));
		String newBelongId=request.getParameter("newDepartmentId");
		int result=jobService.updateInfoById(orderJobId,newJobName,newRoleFlag,newBelongId);
		if(result==0) {
			model.addAttribute("success", null);
			model.addAttribute("error", "职业信息修改失败");
			if(currentUser.getRoleFlag()==1)
				return "FirstManager";
			else if(currentUser.getRoleFlag()==2)
				return "SecondManager";
			else if(currentUser.getRoleFlag()==3)
				return "ThirdManager";
			else if(currentUser.getRoleFlag()==4)
				return "FourthManager";
			else 
				return "FifthManager";
		}else {
			model.addAttribute("error", null);
			model.addAttribute("success", "职业信息修改成功");
			if(currentUser.getRoleFlag()==1)
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
	}
	
	/**
	 * 删除职业接口
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("delJob")
	public String delJob(HttpServletRequest request,Model model) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) {
			if(currentUser==null) {
				model.addAttribute("success", null);
				model.addAttribute("error", "用户登录已失效,请重新登录");
				return "login";
			}
		}
		String selectJobId=request.getParameter("selectJobId");
		int result=userService.getUserCountByJobId(selectJobId);
		System.out.println(result);
		if(result!=0) {
			model.addAttribute("success", null);
			model.addAttribute("error", "该职位还有剩余成员,删除职业失败");
			if(currentUser.getRoleFlag()==1)
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
		result=jobService.updateDelFlagById(selectJobId);
		if(result==0) {
			model.addAttribute("success", null);
			model.addAttribute("error", "职业删除失败");
			if(currentUser.getRoleFlag()==1)
				return "FirstManager";
			else if(currentUser.getRoleFlag()==2)
				return "SecondManager";
			else if(currentUser.getRoleFlag()==3)
				return "ThirdManager";
			else if(currentUser.getRoleFlag()==4)
				return "FourthManager";
			else 
				return "FifthManager";
		}else {
			model.addAttribute("error", null);
			model.addAttribute("success", "职业删除成功");
			if(currentUser.getRoleFlag()==1)
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
	}
	
	/**
	 * 获取部门成员列表，分页查询
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("getDepartmentMemberList")
	public String getDepartmentMemberList(HttpServletRequest request,Model model) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) {
			if(currentUser==null) {
				model.addAttribute("success", null);
				model.addAttribute("error", "用户登录已失效,请重新登录");
				return "login";
			}
		}
		String departmentId=request.getParameter("currentDepartment");
		String queryInfo = request.getParameter("queryInfo");//查询信息
		int offset = Integer.parseInt(request.getParameter("offset"));//初始的
		int limit = Integer.parseInt(request.getParameter("limit"));//限制每页有多个项目
		Map<Object,Object> resultMap=new HashMap<Object,Object>();
		List<QuerySaveMemberInfoDTO> departmentMembers=null;
		departmentMembers=userService.getUserByDepartmentId(departmentId,queryInfo,offset,limit);
		int total=0;
		total=userService.getMemberCountByDepartmentId(departmentId);
		resultMap.put("departmentMembers", departmentMembers);
		resultMap.put("total", total);
		model.addAttribute("departmentMemberList", resultMap);
		if(currentUser.getRoleFlag()==1)
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
	 * 成员基本信息的修改接口
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("updateMemberInfo")
	public String updateMemberInfo(HttpServletRequest request,Model model) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) {
			if(currentUser==null) {
				model.addAttribute("success", null);
				model.addAttribute("error", "用户登录已失效,请重新登录");
				return "login";
			}
		}
		String memberId=request.getParameter("memberId");
		String memberName=request.getParameter("memberName");
		String memberDigits=request.getParameter("memberDigits");
		String memberDepartment=request.getParameter("memberDepartment");
		String memberMajor=request.getParameter("memberMajor");
		String memberClass=request.getParameter("memberClass");
		String memberGrade=request.getParameter("memberGrade");
		String memberPhone=request.getParameter("memberPhone");
		String memberEmail=request.getParameter("memberEmail");
		User orderUser=userService.getUserById(memberId);
		if(orderUser==null) {
			model.addAttribute("success", null);
			model.addAttribute("error", "找不到当前修改的成员");
			if(currentUser.getRoleFlag()==1)
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
		User newInfoUser=new User(memberId,memberName,memberDigits,memberDepartment,
				memberMajor,memberClass,memberGrade,memberPhone,memberEmail);
		int result=userService.updateMemberInfoById(newInfoUser);
		if(result==0) {
			model.addAttribute("success", null);
			model.addAttribute("error", "成员信息修改失败");
			if(currentUser.getRoleFlag()==1)
				return "FirstManager";
			else if(currentUser.getRoleFlag()==2)
				return "SecondManager";
			else if(currentUser.getRoleFlag()==3)
				return "ThirdManager";
			else if(currentUser.getRoleFlag()==4)
				return "FourthManager";
			else 
				return "FifthManager";
		}else {
			model.addAttribute("error", null);
			model.addAttribute("success", "成员信息修改成功");
			if(currentUser.getRoleFlag()==1)
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
	}
	
	/**
	 * 成员职位信息的更改接口
	 * 职位的变迁相应的权限级联
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("updatePostInfo")
	public String updatePostInfo(HttpServletRequest request,Model model) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) {
			if(currentUser==null) {
				model.addAttribute("success", null);
				model.addAttribute("error", "用户登录已失效,请重新登录");
				return "login";
			}
		}
		String memberId=request.getParameter("memberId");
		String memberJobId=request.getParameter("memberJobId");
		User orderUser=userService.getUserById(memberId);
		if(orderUser==null) {
			model.addAttribute("success", null);
			model.addAttribute("error", "找不到当前修改的成员");
			if(currentUser.getRoleFlag()==1)
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
		Job orderJob=jobService.getJobById(memberJobId);
		int result=userService.updateJobById(memberId,memberJobId,orderJob.getRoleFlag());
		if(result==0) {
			model.addAttribute("success", null);
			model.addAttribute("error", "信息修改失败");
			if(currentUser.getRoleFlag()==1)
				return "FirstManager";
			else if(currentUser.getRoleFlag()==2)
				return "SecondManager";
			else if(currentUser.getRoleFlag()==3)
				return "ThirdManager";
			else if(currentUser.getRoleFlag()==4)
				return "FourthManager";
			else 
				return "FifthManager";
		}else {
			model.addAttribute("error", null);
			model.addAttribute("success", "信息修改成功");
			if(currentUser.getRoleFlag()==1)
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
	}
	
	/**
	 * 用户权限的更改(未测试)
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("updateRoleFlag")
	public String updateRoleFlag(HttpServletRequest request,Model model) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) {
			if(currentUser==null) {
				model.addAttribute("success", null);
				model.addAttribute("error", "用户登录已失效,请重新登录");
				return "login";
			}
		}
		String memberId=request.getParameter("memberId");
		int roleFlag=Integer.parseInt(request.getParameter("roleFlag"));
		User orderUser=userService.getUserById(memberId);
		if(orderUser==null) {
			model.addAttribute("success", null);
			model.addAttribute("error", "找不到当前修改的成员");
			if(currentUser.getRoleFlag()==1)
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
		int result=userService.updateJobById(memberId,null,roleFlag);
		if(result==0) {
			model.addAttribute("success", null);
			model.addAttribute("error", "信息修改失败");
			if(currentUser.getRoleFlag()==1)
				return "FirstManager";
			else if(currentUser.getRoleFlag()==2)
				return "SecondManager";
			else if(currentUser.getRoleFlag()==3)
				return "ThirdManager";
			else if(currentUser.getRoleFlag()==4)
				return "FourthManager";
			else 
				return "FifthManager";
		}else {
			model.addAttribute("error", null);
			model.addAttribute("success", "信息修改成功");
			if(currentUser.getRoleFlag()==1)
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
	}
	
	public static void main(String[] args) {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime=df.format(new Date());
		System.out.println(createTime);
	}
}
