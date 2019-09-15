package com.ams.finance.dao;

import com.ams.finance.entity.Cash;

public interface CashDao {
    int deleteByPrimaryKey(String id);

    int insert(Cash record);

    int insertSelective(Cash record);

    Cash selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Cash record);

    int updateByPrimaryKey(Cash record);
}