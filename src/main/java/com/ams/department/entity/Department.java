package com.ams.department.entity;

/**
 * 部门信息表
 * 
 * @author 老吴
 * @create 2019-09-05
 */
public class Department {

	private String id;
    /**
	 * 部门名
	 */
    private String departmentName;
    /**
	 * 现任部长id(与用户表关联)
	 */
    private String ministerId;
    /**
	 * 部门描述
	 */
    private String description;
    /**
	 * 创建时间
	 */
    private String createTime;
    /**
	 * 删除标志 (0-未删除 1-已删除)
	 */
    private Integer delFlag;
    
    public Department() {
    	super();
    }
    
    public Department(String id, String departmentName, String ministerId, String description, String createTime) {
		super();
		this.id = id;
		this.departmentName = departmentName;
		this.ministerId = ministerId;
		this.description = description;
		this.createTime = createTime;
	}
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getMinisterId() {
        return ministerId;
    }

    public void setMinisterId(String ministerId) {
        this.ministerId = ministerId == null ? null : ministerId.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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