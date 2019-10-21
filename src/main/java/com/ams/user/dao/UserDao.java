package com.ams.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ams.department.entity.Department;
import com.ams.user.entity.User;

public interface UserDao {
	User getUserByDigits(String digits);
	
	User getUserById(String id);
	
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

	int updatePasswordByDigits(@Param("digits")String digits,@Param("encryptPassword")String encryptPassword);

	int getUserCountByJobId(String selectJobId);

	List<User> getUserByDepartmentId(@Param("departmentId")String departmentId, @Param("queryInfo")String queryInfo, @Param("offset")int offset, @Param("limit")int limit);

	int getMemberCountByDepartmentId(String departmentId);

    int updateById(User newInfoUser);

	int updateJobById(@Param("memberId")String memberId, @Param("memberJobId")String memberJobId, @Param("roleFlag")Integer roleFlag);

	int insertUserList(List<User> userList);

	List<User> selectListInfo();

	int addSingleUser(User user);

	int updateDelFlagByDigits(@Param("digits")String digits, @Param("delFlag")int delFlag);

	int updateUserInfoByDigits(@Param("digits")String digits, @Param("name")String name, @Param("department")String department, @Param("major")String major, @Param("belongClass")String belongClass,
			@Param("grade")String grade, @Param("phone")String phone, @Param("email")String email);

	int getUsersCount();

	List<User> getUsersByPage(@Param("startPos")int startPos, @Param("pageSize")int pageSize);

	int updateCurrentLoginTimeById(@Param("id")String id, @Param("currentLoginTime")String date);

	List<User> getUserList();

	List<Department> getMemberList(String departmentId);
}