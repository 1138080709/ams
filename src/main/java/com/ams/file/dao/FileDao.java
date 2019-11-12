package com.ams.file.dao;

import java.util.List;

import com.ams.file.entity.File;

public interface FileDao {

	String getGroupNameById(String id);

	String getRemoteFileName(String id);

	String getFolderNameById(String id);

	List<File> getAllFileInfo();

	String getFolderName(String id);

	int insertFileList(List<File> fileList);

	File getFileById(String id);

	int updateFileInfo(File file);

	int deleteFileInfo(String id);

	List<File> getFileInfoByParentId(String id);
	
	
}