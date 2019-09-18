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
}