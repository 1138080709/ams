package com.ams.file.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ams.file.entity.File;
import com.ams.user.entity.User;

public interface IFileService {

	int upload(HttpServletRequest request,User user, File localFile) throws IOException;

	int download(String id, OutputStream output);

	List<File> getAllFileInfo();

	String getFolderNameById(String id);

	File getFileById(String id);

	int deleteFile(File file);

	int updateFileInfo(File file);

	int deleteFileInfo(String id);

	List<File> queryKeyword(String keyword);

	int createFolder(File localFile, User currentUser);

	int deleteFolder(File file);

}
