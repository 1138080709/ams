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
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.ams.utils.Result;

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
	 * @param queryInfo
	 * @param offset 不能为空
	 * @param limit  不能为空
	 * @param request
	 * @return
	 */
	@RequestMapping("getDepartmentList")
	@ResponseBody
	public Result getDepartmentList(String queryInfo,int offset,int limit,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		Map<String, Object> resultMap = new HashMap<String, Object>();
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
		return Result.makeSuccessResult(resultMap);
	}
	/**
	 * 更改部门信息接口
	 * 
	 * @param departmentId
	 * @param newDepartmentName
	 * @param newMinisterDigits
	 * @param newDescribe
	 * @param request
	 * @return
	 */
	@RequestMapping("updateDepartmentInfo")
	@ResponseBody
	public Result UpdateDepartmentInfo(String departmentId,String newDepartmentName,String newMinisterDigits,String newDescribe,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		User ministerUser=userService.getUserByDigits(newMinisterDigits);
		if(ministerUser==null)
			return Result.makeFailResult("找不到该用户");
		String newMinisterId=ministerUser.getId();
		int result=departmentService.updateInfoById(departmentId,newDepartmentName,newMinisterId,newDescribe);
		if(result==0) 
			return Result.makeFailResult("信息修改失败");
		else
			return Result.makeSuccessResult("信息修改成功");
	}
	/**
	 * 创建部门接口
	 * 
	 * @param departmentName 不能为空
	 * @param ministerId
	 * @param describe
	 * @param request
	 * @return
	 */
	@RequestMapping("addDepartment")
	@ResponseBody
	public Result addDepartment(String departmentName,String ministerDigits,String describe,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		String departmentId=IdGen.uuid();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime=df.format(new Date());
		User ministerUser=userService.getUserByDigits(ministerDigits);
		String ministerId=null;
		if(ministerUser!=null)
			ministerId=ministerUser.getId();
		if(departmentName==null||departmentName=="") 
			return Result.makeFailResult("部门名不能为空");
		Department department=departmentService.selectDepartmentByName(departmentName);
		if(department!=null)
			return Result.makeFailResult("部门名已存在");
		Department newDepartment=new Department(departmentId,departmentName,ministerId,describe,createTime);
		int result=departmentService.insertNewDepartment(newDepartment);
		if(result==0) 
			return Result.makeFailResult("部门创建失败");
		else 	
			return Result.makeSuccessResult("部门创建成功");	
	}
	
	/**
	 * 删除部门接口
	 * 注意部门成员的级联处理
	 * 
	 * @param selectDepartmentId 不能为空
	 * @param request
	 * @return
	 */
	@RequestMapping("delDepartment")
	@ResponseBody
	public Result delDepartment(String selectDepartmentId,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		//System.out.println(selectDepartmentId);
		int result=jobService.getJobCountByDepartmentId(selectDepartmentId);
		if(result!=0)
			return Result.makeFailResult("该部门还有剩余职位,删除部门失败");
		result=departmentService.updateDelFlagById(selectDepartmentId);
		//System.out.println(result);
		if(result==0) 
			return Result.makeFailResult("部门删除失败");
		else 
			return Result.makeFailResult("部门删除成功");
	}
	
	/**
	 * 实现职位列表分页查询 返回DTO类(包含职位与所属部门的基本信息)
	 * 
	 * @param queryInfo
	 * @param offset 不能为空
	 * @param limit 不能为空
	 * @param request
	 * @return
	 */
	@RequestMapping("getJobList")
	@ResponseBody
	public Result getJobList(String queryInfo,int offset,int limit,HttpServletRequest request){
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<QuerySaveJobInfoDTO> jobs = null;
		jobs = jobService.getAllJobList(queryInfo, offset, limit); 
		int total = 0;
		total = jobService.getAllJobCount();
		resultMap.put("jobs", jobs);
		resultMap.put("total", total);
		return Result.makeSuccessResult(resultMap);
	}
	
	/**
	 * 创建职位接口
	 * 
	 * @param jobName 不能为空
	 * @param roleFlag 不能为空
	 * @param departmentId 不能为空
	 * @param request
	 * @return
	 */
	@RequestMapping("addJob")
	@ResponseBody
	public Result addJob(String jobName,int roleFlag,String belongId,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		String jobId=IdGen.uuid();
		if(jobName==null||jobName=="") 
			return Result.makeFailResult("职位名不能为空");
		Job job=jobService.getJobByName(jobName);
		if(job!=null)
			return Result.makeFailResult("职位名已存在");
		if(belongId==null)
			return Result.makeFailResult("部门Id不能为空");
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime=df.format(new Date());
		Job newJob=new Job(jobId,jobName,roleFlag,belongId,createTime);
		int result=jobService.insertNewJob(newJob);
		if(result==0) 
			return Result.makeFailResult("职位创建失败");
		else 
			return Result.makeSuccessResult("职位创建成功");
	}
	/**
	 * 修改职业信息接口
	 * 
	 * @param orderJobId
	 * @param newJobName
	 * @param newRoleFlag
	 * @param newBelongId
	 * @param request
	 * @return
	 */
	@RequestMapping("updateJob")
	@ResponseBody
	public Result updateJob(String orderJobId,String newJobName,Integer newRoleFlag,String newBelongId,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		Job orderJob=jobService.getJobById(orderJobId);
		if(orderJobId==null||orderJob==null)
			return Result.makeFailResult("找不到当前的职业信息");
		int result=0;
		if(newRoleFlag==null) 
			result=jobService.updateInfoById(orderJobId, newJobName, null, newBelongId);
		result=jobService.updateInfoById(orderJobId,newJobName,newRoleFlag,newBelongId);
		if(result==0) 
			return Result.makeFailResult("职业信息修改失败");
		else 
			return Result.makeSuccessResult("职业信息修改成功");
			
	}
	
	/**
	 * 删除职位接口
	 * 
	 * @param selectJobId 不能为空
	 * @param request
	 * @return
	 */
	@RequestMapping("delJob")
	@ResponseBody
	public Result delJob(String selectJobId,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		int result=userService.getUserCountByJobId(selectJobId);
		System.out.println(result);
		if(result!=0)
			return Result.makeFailResult("该职位还有剩余成员,删除职业失败");
		result=jobService.updateDelFlagById(selectJobId);
		if(result==0)
			return Result.makeFailResult("职位删除失败");
		else 
			return Result.makeSuccessResult("职位删除成功");
	}
	
	/**
	 * 获取部门成员列表，分页查询
	 *
	 * @param departmentId 不能为空
	 * @param queryInfo
	 * @param offset	      不能为空
	 * @param limit		      不能为空
	 * @param request
	 * @return
	 */
	@RequestMapping("getDepartmentMemberList")
	@ResponseBody
	public Result getDepartmentMemberList(String departmentId,String queryInfo,int offset,int limit,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		Map<String,Object> resultMap=new HashMap<String,Object>();
		List<QuerySaveMemberInfoDTO> departmentMembers=null;
		departmentMembers=userService.getUserByDepartmentId(departmentId,queryInfo,offset,limit);
		int total=0;
		total=userService.getMemberCountByDepartmentId(departmentId);
		resultMap.put("departmentMembers", departmentMembers);
		resultMap.put("total", total);
		return Result.makeSuccessResult(resultMap);
	}
	
	/**
	 * 成员基本信息的修改接口
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("updateMemberInfo")
	@ResponseBody
	public Result updateMemberInfo(HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
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
		if(orderUser==null)
			return Result.makeFailResult("找不到当前修改的成员");
		if(memberName==null&&memberDigits==null&&memberDepartment==null&&
				memberMajor==null&&memberClass==null&&memberGrade==null&&memberPhone==null&&memberEmail==null)
			return Result.makeFailResult("成员信息修改失败");
		User newInfoUser=new User(memberId,memberName,memberDigits,memberDepartment,
				memberMajor,memberClass,memberGrade,memberPhone,memberEmail);
		int result=userService.updateMemberInfoById(newInfoUser);
		if(result==0)
			return Result.makeFailResult("成员信息修改失败");
		else 
			return Result.makeSuccessResult("成员信息修改成功");
	}
	
	/**
	 * 成员职位信息的更改接口
	 * 职位的变迁相应的权限级联
	 * 
	 * @param memberId 不能为空
	 * @param memberJobId 不能为空
	 * @param request
	 * @return
	 */
	@RequestMapping("updatePostInfo")
	@ResponseBody
	public Result updatePostInfo(String memberId,String memberJobId,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		User orderUser=userService.getUserById(memberId);
		if(orderUser==null) 
			return Result.makeFailResult("找不到当前修改的成员");
		Job orderJob=jobService.getJobById(memberJobId);
		int result=userService.updateJobById(memberId,memberJobId,orderJob.getRoleFlag());
		if(result==0)
			return Result.makeFailResult("信息修改失败");
		else 
			return Result.makeSuccessResult("信息修改成功");
	}
	
	/**
	 * 用户权限的更改
	 * 
	 * @param memberId
	 * @param roleFlag
	 * @param request
	 * @return
	 */
	@RequestMapping("updateRoleFlag")
	@ResponseBody
	public Result updateRoleFlag(String memberId,Integer roleFlag,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		User orderUser=userService.getUserById(memberId);
		if(orderUser==null) 
			return Result.makeFailResult("找不到当前修改的成员");
		int result=0;
		if(roleFlag==null)
			return Result.makeFailResult("信息修改失败");
		result=userService.updateJobById(memberId,null,roleFlag);
		if(result==0) 
			return Result.makeFailResult("信息修改失败");
		else 
			return Result.makeSuccessResult("信息修改成功");
	}
	
}
