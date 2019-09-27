package com.ams.finance.entity;

/**
 * 物资表
 *
 * @author 老吴
 * @create 2019-09-05
 */
public class Materials {
    private String id;
    /**
	 * 物资名
	 */
    private String materialName;
    /**
	 * 物资总数量
	 */
    private Integer totalNumber;
    /**
	 * 剩余数量
	 */
    private Integer remainNumber;
    /**
	 * 描述
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName == null ? null : materialName.trim();
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getRemainNumber() {
        return remainNumber;
    }

    public void setRemainNumber(Integer remainNumber) {
        this.remainNumber = remainNumber;
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