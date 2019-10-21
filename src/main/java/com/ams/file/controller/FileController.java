package com.ams.file.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ams.department.dto.QuerySaveDepartmentInfoDTO;
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
	 * 上传文件 文件名要求：目录路径+文件(带后缀)
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("upload")
	public Result upload(HttpServletRequest request) throws IOException {
		HttpSession session=request.getSession();
		User currentUser=(User)session.getAttribute("currentUser");
		if(currentUser==null) 
			return Result.makeFailResult("用户登录已失效,请重新登录");
		int result=fileService.upload(request,currentUser);
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
		String fileName=fileService.getFileName(id);
		if(fileName==null)
			return Result.makeFailResult("文件获取失败");
		response.reset();
		//指定下载的文件名，浏览器都会使用本地编码，即GBK，浏览器收到这个文件名后，用ISO-8859-1来解码，然后用GBK来显示
		//所以我们用GBK解码，用ISO-8859-1来编码，在浏览器那边会返过来执行
		response.setHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("GBK"),"ISO-8859-1"));
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
	 * 获取文件管理一级目录
	 * @param request
	 * @return
	 */
//	public Result getCatalog(int offset,int limit,HttpServletRequest request) {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		List<String> paths = null;
//		List<String> catalogs=null;
//		int total = 0;
//		paths = fileService.getAllPathList();
//		for(String path:paths) {
//			String catalog=FileUtils.getCatalog(path);
//			if(!FileUtils.judgeCatalogList(catalog, catalogs)) {
//				catalogs.add(catalog);
//				total++;
//			}
//		}
//		
//		total = fileService.getAllPathCount();
//		resultMap.put("departments", departments);
//		resultMap.put("total", total);
//		return Result.makeSuccessResult(resultMap);
//	}
}
