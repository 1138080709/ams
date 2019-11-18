package com.ams.finance.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ams.finance.entity.Materials;

public interface MaterialsDao {
	/*
	 * int deleteByPrimaryKey(String id);
	 * 
	 * int insert(Materials record);
	 * 
	 * Materials selectByPrimaryKey(String id);
	 * 
	 * 
	 * 
	 * int updateByPrimaryKey(Materials record);
	 */

	int insertSelective(Materials record);

	Materials selectById(String id);

	int updateMaterials(Materials record);

	int getMaterialsCount();

	List<Materials> getMaterialsList(@Param("queryInfo")String queryInfo);

	int updateMaterialsDelFlag(List<String> idList);
}