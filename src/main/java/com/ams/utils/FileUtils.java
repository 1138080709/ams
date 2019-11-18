package com.ams.utils;

import java.util.List;

public class FileUtils {
	
	/**
	 * 返回文件名
	 * 
	 * @param fileName 带路径的文件名
	 * @return originFileNmae 文件名
	 */
	public static String getOriginFileName(String fileName) {
		int index=fileName.lastIndexOf('.');
		String originFileName=fileName.substring(0,index);
		return originFileName;
	}
	
	/**
	 * 返回后缀名(不带.)
	 * 
	 * @param fileName 文件名
	 * @return 后缀名
	 */
	public static String getFileSuffix(String fileName) {
		int index=fileName.lastIndexOf('.');
		String fileSuffix=fileName.substring(index+1);
		return fileSuffix;
	}
	
	/**
	 * 返回文件路径
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFilePath(String fileName) {
		int index=fileName.lastIndexOf('/');
		String filePath=fileName.substring(0, index);
		return filePath;
	}
	
	/**
	 * 返回路径第一层
	 * @param Path
	 * @return
	 */
	public static String getCatalog(String Path) {
		int index=Path.indexOf('/');
		String filePath=null;
		if(index==0) {
			index=Path.indexOf('/', 1);
			filePath=Path.substring(1,index);
		}else
			filePath=Path.substring(0,index);
		return filePath;
	}
	
	/**
	 * 判断目录是否重复
	 * @param catalog
	 * @param catalogs
	 * @return
	 */
	public static boolean judgeCatalogList(String catalog,List<String> catalogs) {
		for(String str:catalogs) {
			if(str.equals(catalog))
				return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		String fileName="帅哥.docx";
		String result=getOriginFileName(fileName);
		System.out.println(result);
	}
}
