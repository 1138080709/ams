package com.ams.file.dao;

import java.util.List;

import com.ams.file.entity.File;

public interface FileDao {

	int insertFileList(List<File> fileList);
	/*
	 * int deleteByPrimaryKey(String id);
	 * 
	 * int insert(File record);
	 * 
	 * int insertSelective(File record);
	 * 
	 * File selectByPrimaryKey(String id);
	 * 
	 * int updateByPrimaryKeySelective(File record);
	 * 
	 * int updateByPrimaryKey(File record);
	 */

	List<String> getAllPathList();

	int getAllPathCount();

	String getFileNameById(String id);

	String getGroupNameById(String id);

	String getRemoteFileName(String id);

}