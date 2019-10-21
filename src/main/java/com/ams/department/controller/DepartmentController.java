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
//	/**
//	 * 实现部门列表分页查询 返回DTO类(包含部门与部长的基本信息)
//	 * 测试问题在于，如何将offset和limit的数据从前端传进后端
//	 * 
//	 * @param queryInfo
//	 * @param offset 不能为空
//	 * @param limit  不能为空
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("getDepartmentList")
//	@ResponseBody
//	public Result getDepartmentList(String queryInfo,int offset,int limit,HttpServletRequest request) {
//		HttpSession session=request.getSession();
//		User currentUser=(User)session.getAttribute("currentUser");
//		if(currentUser==null) 
//			return Result.makeFailResult("用户登录已失效,请重新登录");
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		List<QuerySaveDepartmentInfoDTO> departments = null;
//		departments = departmentService.getAlldepartmentList(queryInfo, offset, limit); 
//		int total = 0;
//		total = departmentService.getAlldepartmentCount();
//		/*测试代码
//		int i=1;
//		for(QuerySaveDepartmentInfoDTO department:departments) {
//			System.out.println(i+":"+department.getDepartmentName());
//		}
//		System.out.println("总数为:"+total);
//		*/
//		resultMap.put("departments", departments);
//		resultMap.put("total", total);
//		return Result.makeSuccessResult(resultMap);
//	}
	
	@RequestMapping("getDepartmentList")
	@ResponseBody
	public Result getDepartmentList(HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Department> departmentList=departmentService.getDepartmentList();
		resultMap.put("departmentList", departmentList);
		return Result.makeSuccessResult(resultMap);
	}
	
	/**
	 * 更改部门信息接口
	 * 
	 * @param department
	 * @param ministerDigits
	 * @param request
	 * @return
	 */
	@RequestMapping("updateDepartmentInfo")
	@ResponseBody
	public Result UpdateDepartmentInfo(Department department,String ministerDigits,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		User ministerUser=null;
		if(ministerDigits!=null) {
			ministerUser=userService.getUserByDigits(ministerDigits);
			if(ministerUser==null)
				return Result.makeFailResult("找不到该成员信息");
		}
		int result=departmentService.updateInfoById(department,ministerUser);
		if(result==0)
			return Result.makeFailResult("信息修改失败");
		else
			return Result.makeSuccessResult("信息修改成功");
		
	}
	
	/**
	 * 创建部门接口
	 * 
	 * @param department
	 * @param ministerDigits
	 * @param request
	 * @return
	 */
	@RequestMapping("addDepartment")
	@ResponseBody
	public Result addDepartment(Department department,String ministerDigits,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		User ministerUser=null;
		Department orderDepartment=departmentService.selectDepartmentByName(department.getDepartmentName());
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		if(orderDepartment!=null)
			return Result.makeFailResult("部门名已存在");
		if(ministerDigits!=null) {
			ministerUser=userService.getUserByDigits(ministerDigits);
			if(ministerUser==null)	
				return Result.makeFailResult("找不到该成员信息，无法进行设置");
		}
		int result=departmentService.insertNewDepartment(department,ministerUser);
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
	
//	/**
//	 * 实现职位列表分页查询 返回DTO类(包含职位与所属部门的基本信息)
//	 * 
//	 * @param queryInfo
//	 * @param offset 不能为空
//	 * @param limit 不能为空
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("getJobList")
//	@ResponseBody
//	public Result getJobList(String queryInfo,int offset,int limit,HttpServletRequest request){
//		HttpSession session=request.getSession();
//		User currentUser=(User)session.getAttribute("currentUser");
//		if(currentUser==null) 
//			return Result.makeFailResult("用户登录已失效,请重新登录");
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		List<QuerySaveJobInfoDTO> jobs = null;
//		jobs = jobService.getAllJobList(queryInfo, offset, limit); 
//		int total = 0;
//		total = jobService.getAllJobCount();
//		resultMap.put("jobs", jobs);
//		resultMap.put("total", total);
//		return Result.makeSuccessResult(resultMap);
//	}
	
	/**
	 * 实现职位列表
	 * 
	 * @param queryInfo
	 * @param offset 不能为空
	 * @param limit 不能为空
	 * @param request
	 * @return
	 */
	@RequestMapping("getJobList")
	@ResponseBody
	public Result getJobList(HttpServletRequest request){
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Job> jobList = jobService.getJobList();
		resultMap.put("jobList",jobList);
		return Result.makeSuccessResult(resultMap);
	}
	/**
	 * 创建职位接口
	 * 
	 * @param job
	 * @param request
	 * @return
	 */
	@RequestMapping("addJob")
	@ResponseBody
	public Result addJob(Job job,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		Job orderJob=jobService.getJobByName(job.getJobName());
		if(orderJob!=null)
			return Result.makeFailResult("职位名已存在");
		int result=jobService.insertNewJob(job);
		if(result==0) 
			return Result.makeFailResult("职位创建失败");
		else 
			return Result.makeSuccessResult("职位创建成功");
	}
	
	/**
	 * 修改职位信息接口
	 * 
	 * @param job
	 * @param request
	 * @return
	 */
	@RequestMapping("updateJob")
	@ResponseBody
	public Result updateJob(Job job,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		Job orderJob=jobService.getJobById(job.getId());
		if(orderJob==null)
			return Result.makeFailResult("找不到当前的职位信息");
		int result=jobService.updateInfoById(job);
		if(result==0)
			return Result.makeFailResult("职位信息修改失败");
		else 
			return Result.makeSuccessResult("职位信息修改成功");
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
	
//	/**
//	 * 获取部门成员列表，分页查询
//	 *
//	 * @param departmentId 不能为空
//	 * @param queryInfo
//	 * @param offset	      不能为空
//	 * @param limit		      不能为空
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("getDepartmentMemberList")
//	@ResponseBody
//	public Result getDepartmentMemberList(String departmentId,String queryInfo,int offset,int limit,HttpServletRequest request) {
//		HttpSession session=request.getSession();
//		User currentUser=(User)session.getAttribute("currentUser");
//		if(currentUser==null) 
//			return Result.makeFailResult("用户登录已失效,请重新登录");
//		Map<String,Object> resultMap=new HashMap<String,Object>();
//		List<QuerySaveMemberInfoDTO> departmentMembers=null;
//		departmentMembers=userService.getUserByDepartmentId(departmentId,queryInfo,offset,limit);
//		int total=0;
//		total=userService.getMemberCountByDepartmentId(departmentId);
//		resultMap.put("departmentMembers", departmentMembers);
//		resultMap.put("total", total);
//		return Result.makeSuccessResult(resultMap);
//	}
	
	/**
	 * 获取部门成员列表
	 *
	 * @param departmentId 不能为空
	 * @return
	 */
	@RequestMapping("getMemberList")
	@ResponseBody
	public Result getDepartmentMemberList(String departmentId,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		Map<String,Object> resultMap=new HashMap<String,Object>();
		List<Department> memberList=userService.getMemberList(departmentId);
		resultMap.put("memberList", memberList);
		return Result.makeSuccessResult(resultMap);
	}
	
	/**
	 * 成员基本信息的修改接口
	 * 
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("updateMemberInfo")
	@ResponseBody
	public Result updateMemberInfo(User user,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		//System.out.println(user.getId());
		User orderUser=userService.getUserById(user.getId());
		if(orderUser==null)
			return Result.makeFailResult("找不到当前修改的成员");
		int result=userService.updateMemberInfoById(user);
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
	
	/**
	 * 获取用户所在部门名
	 * @param request
	 * @return
	 */
	@RequestMapping("getDepartment")
	@ResponseBody
	public Result getDepartment(HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		Map<String,Object> resultMap=new HashMap<String,Object>();
		if(currentUser.getBelongId()==null)
			return Result.makeSuccessResult("该用户暂无部门");
		Department department=departmentService.getDepatmentById(currentUser.getBelongId());
		if(department==null)
			return Result.makeFailResult("该用户部门ID无效");
		resultMap.put("department", department);
		return Result.makeSuccessResult(resultMap);
	}
	
	/**
	 * 获取用户职位名
	 * @param request
	 * @return
	 */
	@RequestMapping("getJob")
	@ResponseBody
	public Result getJob(HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		Map<String,Object> resultMap=new HashMap<String,Object>();
		if(currentUser.getJobId()==null)
			return Result.makeSuccessResult("该用户暂无职位");
		Job job=jobService.getJobById(currentUser.getJobId());
		if(job==null)
			return Result.makeFailResult("该用户职位ID无效");
		resultMap.put("job", job);
		return Result.makeSuccessResult(resultMap);
	}
}
