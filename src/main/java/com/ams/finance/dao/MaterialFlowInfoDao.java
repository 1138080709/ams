package com.ams.finance.dao;

import com.ams.finance.entity.MaterialFlowInfo;

public interface MaterialFlowInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(MaterialFlowInfo record);

    int insertSelective(MaterialFlowInfo record);

    MaterialFlowInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MaterialFlowInfo record);

    int updateByPrimaryKey(MaterialFlowInfo record);
}