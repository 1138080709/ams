package com.ams.finance.dao;

import com.ams.finance.entity.CashFlowInfo;

public interface CashFlowInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(CashFlowInfo record);

    int insertSelective(CashFlowInfo record);

    CashFlowInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CashFlowInfo record);

    int updateByPrimaryKey(CashFlowInfo record);
}