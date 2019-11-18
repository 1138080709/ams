package com.ams.file.service.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	public int upload(HttpServletRequest request, User user, File localFile) throws IOException {	
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
			file.setFolderName(FileUtils.getOriginFileName(fileName));
			file.setDescription(localFile.getDescription());
			file.setPortLevel(localFile.getPortLevel());
			file.setParentId(localFile.getParentId());
			file.setIsDirectory(0);
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			file.setCreateTime(df.format(new Date()));
			file.setCreator(user.getName());
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
	public int download(String id,OutputStream output) {
		String fileName=this.fileDao.getFolderNameById(id);
		String groupName=this.fileDao.getGroupNameById(id);
		String remoteFileName=this.fileDao.getRemoteFileName(id);
		if(fileName==null||groupName==null||remoteFileName==null)
			return 0;
		FastDFSClient.downloadFile(fileName,groupName, remoteFileName, output);
		return 1;
	}

	@Override
	public List<File> getAllFileInfo() {
		return this.fileDao.getAllFileInfo();
	}

	@Override
	public String getFolderNameById(String id) {
		return this.fileDao.getFolderNameById(id);
	}

	@Override
	public File getFileById(String id) {
		return this.fileDao.getFileById(id);
	}

	@Override
	public int deleteFile(File file) {
		return FastDFSClient.deleFile(file.getFolderName(), file.getGroupName(), file.getRemoteFileName());
	}

	@Override
	public int updateFileInfo(File file) {
		return this.fileDao.updateFileInfo(file);
	}

	@Override
	public int deleteFileInfo(String id) {
		return this.fileDao.deleteFileInfo(id);
		
	}

	@Override
	public List<File> queryKeyword(String keyword) {
		List<File> allFiles=this.fileDao.getAllFileInfo();
		List<File> files=new ArrayList<File>();
		for(File file:allFiles) {
			if(file.getDescription()==null||file.getDescription().equals(""))
				continue;
			if(KMP(keyword,file.getFolderName())==true) {
				files.add(file);
				continue;
			}
			if(KMP(keyword,file.getDescription())==true) {
				files.add(file);
				continue;
			}
		}
		return files;
	}
	
	/**
	 * KMP匹配
	 * @param keyword
	 * @param description
	 * @return
	 */
	public static boolean KMP(String keyword,String description) {
		char[] keyChar=keyword.toCharArray();
		char[] descriptionChar=description.toCharArray();
		
		int[] next = next(keyChar);
		int i = 0;
		int j = 0;
		while (i <= descriptionChar.length - 1 && j <= keyChar.length - 1) {
			if (j == -1 || descriptionChar[i] == keyChar[j]) {
				i++;
				j++;
			} else {
				j = next[j];
			}
		}
		if (j < keyChar.length) {
			return false;
		} else
			return true; 
	}
	
	/**
	 * 获得字符串的next函数值
	 * 
	 * @param keyChar
	 * @return
	 */
	public static int[] next(char[] keyChar) {
		int[] next = new int[keyChar.length];
		next[0] = -1;
		int i = 0;
		int j = -1;
		while (i < keyChar.length - 1) {
			if (j == -1 || keyChar[i] == keyChar[j]) {
				i++;
				j++;
				if (keyChar[i] != keyChar[j]) {
					next[i] = j;
				} else {
					next[i] = next[j];
				}
			} else {
				j = next[j];
			}
		}
		return next;
	}

	/*
	 * public static void main(String[] args) {
	 * System.out.println("匹配情况："+KMP("吴伟杰","吴伟铨好帅")); }
	 */
	@Override
	public int createFolder(File localFile,User user) {
		File file=new File();
		List<File> files=new ArrayList<File>();
		String id =IdGen.uuid();
		System.out.println("id:"+id);
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		file.setId(id);
		file.setFolderName(localFile.getFolderName());
		file.setDescription(localFile.getDescription());
		file.setPortLevel(localFile.getPortLevel());
		file.setParentId(localFile.getParentId());
		file.setIsDirectory(1);
		file.setCreator(user.getName());
		file.setCreateTime(df.format(new Date()));
		files.add(file);
		return this.fileDao.insertFileList(files);
	}

	@Override
	public int deleteFolder(File file) {
		List<File> files=this.fileDao.getFileInfoByParentId(file.getId());
		if(files.size()!=0)
			return 0;
		else
			return this.fileDao.deleteFileInfo(file.getId());
	}

}
