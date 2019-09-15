package com.ams.finance.dao;

import com.ams.finance.entity.Materials;

public interface MaterialsDao {
    int deleteByPrimaryKey(String id);

    int insert(Materials record);

    int insertSelective(Materials record);

    Materials selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Materials record);

    int updateByPrimaryKey(Materials record);
}