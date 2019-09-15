package com.ams.finance.entity;

/**
 * 社团资金表
 * 
 * @author 老吴
 * @create 2019-09-05
 */
public class Cash {
    private String id;
    /**
	 * 余额
	 */
    private String money;
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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money == null ? null : money.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}