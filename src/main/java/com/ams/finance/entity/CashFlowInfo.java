package com.ams.finance.entity;
/**
 * 资金流动信息表
 * 
 * @author 老吴
 * @create 2019-09-05
 * 
 */
public class CashFlowInfo {
    private String id;
    /**
	 * 信息类型(0-支出 1-收入)
	 */
    private Integer infoType;
    /**
	 * 流动金额
	 */
    private Integer money;
    /**
	 * 描述
	 */
    private String description;
    /**
	 * 申请人id(与用户表关联)
	 */
    private String proposerId;
    /**
	 * 申请时间
	 */
    private String applicateTime;
    /**
	 * 审核人id(与用户表关联)
	 */
    private String auditorId;
    /**
	 * 删除标志 0-未删除 1-已删除
	 */
    private Integer delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getInfoType() {
        return infoType;
    }

    public void setInfoType(Integer infoType) {
        this.infoType = infoType;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getProposerId() {
        return proposerId;
    }

    public void setProposerId(String proposerId) {
        this.proposerId = proposerId == null ? null : proposerId.trim();
    }

    public String getApplicateTime() {
        return applicateTime;
    }

    public void setApplicateTime(String applicateTime) {
        this.applicateTime = applicateTime == null ? null : applicateTime.trim();
    }

    public String getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(String auditorId) {
        this.auditorId = auditorId == null ? null : auditorId.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}