package com.ams.department.dto;

import com.ams.department.entity.Department;

public class QuerySaveJobInfoDTO {
	private String id;
    /**
	 * 职业名称
	 */
    private String jobName;
    /**
	 * 职业对应的用户权限（0-系统管理员 1-一级管理员 2-二级管理员 3-三级管理员 4-四级管理员 5-五级管理员）
	 */
    private Integer roleFlag;
    /**
	 * 职业所属部门
	 */
    private Department belongId;
    /**
	 * 创建时间
	 */
    private String createTime;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public Integer getRoleFlag() {
		return roleFlag;
	}
	public void setRoleFlag(Integer roleFlag) {
		this.roleFlag = roleFlag;
	}
	public Department getBelongId() {
		return belongId;
	}
	public void setBelongId(Department belongId) {
		this.belongId = belongId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
