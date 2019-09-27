package com.ams.user.service;


import java.io.InputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.ams.department.dto.QuerySaveMemberInfoDTO;
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

	public int updateJobById(String memberId, String memberJobId, Integer roleFlag);

	public void importExcelInfo(InputStream in, MultipartFile file);

	public XSSFWorkbook exportExcelInfo();

	public int addSingleUser(User user);

	public int updateDelFlagByDigits(String digits, int i);

	public int updateUserInfoByDigits(String digits, String name, String department, String major, String belongClass,
			String grade, String phone, String email, User user);

	public int getUsersCount();

	public List<User> getUsersByPage(int startPos, int pageSize);
}
