package com.ams.finance.entity;

/**
 * 物资流动信息表
 * 
 * @author 老吴
 * @create 2019-09-05
 */
public class MaterialFlowInfo {
    private String id;
    /**
	 * 物资id(与物资表关联)
	 */
    private String materialId;
    /**
	 * 信息类型(0-出借 1-归还)
	 */
    private Integer infoType;
    /**
	 * 物资数量
	 */
    private Integer number;
    /**
	 * 用途
	 */
    private String purpose;
    /**
	 * 日期
	 */
    private String date;
    /**
	 * 借方组织名称
	 */
    private String organizationName;
    /**
	 * 借方负责人姓名
	 */
    private String principalName;
    /**
	 * 借方负责人联系方式
	 */
    private String principalPhone;
    /**
	 * 审核人id(与用户表关联)
	 */
    private String auditorId;
    /**
	 * 备注
	 */
    private String remarks;
    /**
	 * 删除标志 (0-未删除 1-已删除)
	 */
    private Integer delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId == null ? null : materialId.trim();
    }

    public Integer getInfoType() {
        return infoType;
    }

    public void setInfoType(Integer infoType) {
        this.infoType = infoType;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName == null ? null : organizationName.trim();
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName == null ? null : principalName.trim();
    }

    public String getPrincipalPhone() {
        return principalPhone;
    }

    public void setPrincipalPhone(String principalPhone) {
        this.principalPhone = principalPhone == null ? null : principalPhone.trim();
    }

    public String getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(String auditorId) {
        this.auditorId = auditorId == null ? null : auditorId.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}