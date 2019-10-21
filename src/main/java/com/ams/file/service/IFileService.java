package com.ams.file.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ams.user.entity.User;

public interface IFileService {

	int upload(HttpServletRequest request,User user) throws IOException;

	List<String> getAllPathList();

	int getAllPathCount();

	int download(String id, OutputStream output);

	String getFileName(String id);

}
