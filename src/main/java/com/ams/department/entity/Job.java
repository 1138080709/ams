package com.ams.department.entity;

/**
 * 职位表
 * 
 * @author 老吴
 * @create 2019-09-05
 */
public class Job {

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
	 * 部门id(职业所属部门)
	 */
	private String belongId;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 删除标志 (0-未删除 1-已删除)
	 */
	private Integer delFlag;

	public Job() {
		super();
	}

	public Job(String id, String jobName, Integer roleFlag, String belongId, String createTime) {
		super();
		this.id = id;
		this.jobName = jobName;
		this.roleFlag = roleFlag;
		this.belongId = belongId;
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName == null ? null : jobName.trim();
	}

	public Integer getRoleFlag() {
		return roleFlag;
	}

	public void setRoleFlag(Integer roleFlag) {
		this.roleFlag = roleFlag;
	}

	public String getBelongId() {
		return belongId;
	}

	public void setBelongId(String belongId) {
		this.belongId = belongId == null ? null : belongId.trim();
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
}