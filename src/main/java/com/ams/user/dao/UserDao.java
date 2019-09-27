package com.ams.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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

	void insertUserList(List<User> userList);

	List<User> selectListInfo();

	int addSingleUser(User user);

	int updateDelFlagByDigits(String digits, int delFlag);

	int updateUserInfoByDigits(String digits, String name, String department, String major, String belongClass,
			String grade, String phone, String email);

	int getUsersCount();

	List<User> getUsersByPage(int startPos, int pageSize);
}