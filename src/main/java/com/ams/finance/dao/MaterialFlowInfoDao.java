package com.ams.finance.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ams.finance.entity.MaterialFlowInfo;

public interface MaterialFlowInfoDao {
//    int deleteByPrimaryKey(String id);
//
//    int insert(MaterialFlowInfo record);
//
//    
//
//    MaterialFlowInfo selectByPrimaryKey(String id);
//
//    int updateByPrimaryKeySelective(MaterialFlowInfo record);
//
//    int updateByPrimaryKey(MaterialFlowInfo record);
//    
    
    
    int insertSelective(MaterialFlowInfo record);

	int getMaterialsFlowInfoCount();

	List<MaterialFlowInfoDao> getMaterialFlowInfoList(@Param("queryInfo")String queryInfo, @Param("offset")int offset, @Param("limit")int limit);
}