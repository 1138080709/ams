package com.ams.user.service;


import java.io.InputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.ams.department.dto.QuerySaveMemberInfoDTO;
import com.ams.department.entity.Department;
import com.ams.department.entity.Job;
import com.ams.user.entity.User;

public interface IUserService {
	public User getUserById(String userId);
	
	public User getUserByDigits(String digits);

	public int updatePasswordByDigits(String digits,String encryptPassword);

	public int getUserCountByJobId(String selectJobId);

	public List<QuerySaveMemberInfoDTO> getUserByDepartmentId(String departmentId, String queryInfo, int offset,
			int limit);

	public int getMemberCountByDepartmentId(String departmentId);

	public int updateMemberInfoById(User newInfoUser);

	public void importExcelInfo(InputStream in, MultipartFile file);

	public XSSFWorkbook exportExcelInfo(List<String> idList);

	public int addSingleUser(User user);

	public int updateDelFlagByDigits(String digits, int i);

	public int updateUserInfoByDigits(String digits, String name, String department, String major, String belongClass,
			String grade, String phone, String email, User user);

	public int getUsersCount();

	public List<User> getUsersByPage(int startPos, int pageSize);

	public int addUserByApplication(List<String> idList);

	public int updateCurrentLoginTimeById(String id, String date);

	public List<User> getUserList();

//	public List<Department> getMemberList(String departmentId);

	public int updateJobById(String memberId, Job orderJob);

	public int updateRoleFlagById(String memberId, Integer roleFlag);

	public List<User> getMemberList();

	public List<User> getMemberListByDepartmentId(String departmentId);
}
