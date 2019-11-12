package com.ams.file.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ams.department.dto.QuerySaveDepartmentInfoDTO;
import com.ams.file.entity.File;
import com.ams.file.service.IFileService;
import com.ams.user.entity.User;
import com.ams.utils.FileUtils;
import com.ams.utils.Result;

@RestController
@RequestMapping("file")
public class FileController {
	@Resource
	private IFileService fileService;
	
	/**
	 * 上传文件 文件名要求：带后缀
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("upload")
	public Result upload(File localFile,HttpServletRequest request) throws IOException {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		int result=fileService.upload(request,currentUser,localFile);
		if(result==0)
			return Result.makeFailResult("文件上传发生错误");	
		else
			return Result.makeSuccessResult("文件上传成功");
	}
	/**
	 * 下载文件
	 * 
	 * @param ids
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("download")
	public Result download(String id,HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		File file=fileService.getFileById(id);
//		String fileName=fileService.getFolderNameById(id);
//		if(fileName==null)
//			return Result.makeFailResult("文件获取失败");
		if(file==null)
			return Result.makeFailResult("文件获取失败");
		if(file.getIsDirectory()!=0)
			return Result.makeFailResult("文件类型错误");
		response.reset();
		//指定下载的文件名，浏览器都会使用本地编码，即GBK，浏览器收到这个文件名后，用ISO-8859-1来解码，然后用GBK来显示
		//所以我们用GBK解码，用ISO-8859-1来编码，在浏览器那边会返过来执行
		response.setHeader("Content-Disposition","attachment;filename="+new String(file.getFolderName().getBytes("GBK"),"ISO-8859-1"));
		response.setContentType("application/octet-stream;charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires",0);
		OutputStream output=response.getOutputStream();
		int result=fileService.download(id,output);
		if(result==0)
			return Result.makeFailResult("下载发生错误");
		else
			return Result.makeSuccessResult("文件下载成功");
	}
	
	/**
	 * 获取所有文件信息
	 * @param request
	 * @return
	 */
	@RequestMapping("getFileInfo")
	public Result getFileInfo(HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		Map<String,Object> resultMap=new HashMap<String,Object>();
		List<File> files=fileService.getAllFileInfo();
		resultMap.put("files", files);
		return Result.makeSuccessResult(resultMap);
	}
	
	/**
	 * 删除指定文件
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("deleteFile")
	public Result deleteFile(String id,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		
		File file=fileService.getFileById(id);
		if(file==null)
			return Result.makeFailResult("文件不存在");
		int result=fileService.deleteFile(file);
		if(result==0) {
			result=fileService.deleteFileInfo(id);
			if(result!=0)
				return Result.makeSuccessResult();
		}
		return Result.makeFailResult("文件删除发生错误");
	}
	
	/**
	 * 更新文件基本信息
	 * @param localFile
	 * @param request
	 * @return
	 */
	@RequestMapping("updateFileInfo")
	public Result updateFileInfo(File localFile,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		File file=fileService.getFileById(localFile.getId());
		if(file==null)
			return Result.makeFailResult("文件信息更改失败");
		if(localFile.getFolderName()!=null&&!localFile.getFolderName().equals(""))
			file.setFolderName(localFile.getFolderName());
		if(localFile.getDescription()!=null&&!localFile.getDescription().equals(""))
			file.setDescription(localFile.getDescription());
		if(currentUser.getName()!=null&&!currentUser.getName().equals(""))
			file.setUpdator(currentUser.getName());
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		file.setCreateTime(df.format(new Date()));
		int result=fileService.updateFileInfo(file);
		if(result!=0)
			return Result.makeSuccessResult();
		else
			return Result.makeFailResult("更新失败");
			
	}
	
	/**
	 * 新建文件夹
	 * @param localFile
	 * @return
	 */
	@RequestMapping("createFolder")
	public Result createFolder(File file,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");	
		int result=fileService.createFolder(file,currentUser);
		if(result==0)
			return Result.makeFailResult("创建发生错误");
		else
			return Result.makeSuccessResult();
	}
	
	/**
	 * 删除文件夹
	 * @param keyword
	 * @param request
	 * @return
	 */
	@RequestMapping("deleteFolder")
	public Result deleteFolder(String id,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		File file=fileService.getFileById(id);
		if(file==null)
			return Result.makeFailResult("该文件夹不存在");
		int result=fileService.deleteFolder(file);
		if(result==0)
			return Result.makeFailResult("删除发生错误，删除失败");
		else
			return Result.makeSuccessResult();
		
	}
	
	/**
	 * 关键字搜索
	 * 
	 * @param keyword
	 * @param request
	 * @return
	 */
	@RequestMapping("queryKeyword")
	public Result queryKeyword(String keyword,HttpServletRequest request) {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		if(keyword==null||keyword.equals(""))
			return Result.makeFailResult("关键字为空，无法查询");
		List<File> files=fileService.queryKeyword(keyword);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("resultMap", files);
		return Result.makeSuccessResult(resultMap);
	}
}
