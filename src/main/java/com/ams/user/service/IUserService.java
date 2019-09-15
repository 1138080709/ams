package com.ams.user.service;


import java.util.List;

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
}
