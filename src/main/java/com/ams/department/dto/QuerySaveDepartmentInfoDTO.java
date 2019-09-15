package com.ams.department.dto;

import com.ams.user.entity.User;

public class QuerySaveDepartmentInfoDTO {

	private String id;
	/**
	 * 部门名
	 */
	private String departmentName;
	/**
	 * 现任部长id对应的信息
	 */
	private User minister;
	/**
	 * 部门描述
	 */
	private String description;
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

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public User getMinister() {
		return minister;
	}

	public void setMinister(User minister) {
		this.minister = minister;
	}
}
