package com.ams.file.service.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.csource.common.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import com.ams.file.client.FastDFSClient;
import com.ams.file.dao.FileDao;
import com.ams.file.entity.File;
import com.ams.file.service.IFileService;
import com.ams.user.entity.User;
import com.ams.utils.FileUtils;
import com.ams.utils.IdGen;
@Service("fileService")
public class FileServiceImpl implements IFileService{
	@Resource
	private FileDao fileDao;
	
	public int upload(HttpServletRequest request,User user) throws IOException {	
		MultipartRequest multipart=(MultipartRequest)request;
		Map<String, MultipartFile> files=null;
		List<File> fileList=new ArrayList<File>();
		File file=null;
		NameValuePair[] metas=null;
		files=multipart.getFileMap();
		for(String fileName:files.keySet()) {
			file=new File();
			String id =IdGen.uuid();
			file.setId(id);
			file.setFileName(FileUtils.getOriginFileName(fileName));
			file.setPath(FileUtils.getFilePath(fileName));
			file.setAuthor(user.getName());
			NameValuePair meta1=new NameValuePair();
			meta1.setName("id");
			meta1.setValue(id);
			NameValuePair meta2=new NameValuePair();
			meta2.setName("fileName");
			meta2.setValue(fileName);
			NameValuePair meta3=new NameValuePair();
			meta3.setName("author");
			meta3.setValue(user.getName());
			metas=new NameValuePair[3];
			metas[0]=meta1;
			metas[1]=meta2;
			metas[2]=meta3;
			MultipartFile multipartFile=files.get(fileName);
			String result=FastDFSClient.uploadFile(multipartFile.getBytes(),FileUtils.getFileSuffix(fileName),metas);
			System.out.println(result);
			String[] fd=FastDFSClient.separationResult(result);
			file.setGroupName(fd[0]);
			file.setRemoteFileName(fd[1]);
			fileList.add(file);
		}
		int result=0;
		if(fileList.size()==0||fileList==null)
			return result;
		result=fileDao.insertFileList(fileList);
		return result;
	}

	@Override
	public List<String> getAllPathList() {
		return this.fileDao.getAllPathList();
	}

	@Override
	public int getAllPathCount() {
		return this.fileDao.getAllPathCount();
	}

	@Override
	public int download(String id,OutputStream output) {
		String fileName=this.fileDao.getFileNameById(id);
		String groupName=this.fileDao.getGroupNameById(id);
		String remoteFileName=this.fileDao.getRemoteFileName(id);
		if(fileName==null||groupName==null||remoteFileName==null)
			return 0;
		FastDFSClient.downloadFile(fileName,groupName, remoteFileName, output);
		return 1;
	}

	@Override
	public String getFileName(String id) {
		return this.fileDao.getFileNameById(id);
	}

}
