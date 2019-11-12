package com.ams.file.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class FastDFSClient {
	private static final Logger log = LoggerFactory.getLogger(FastDFSClient.class);
	private static TrackerClient trackerClient;

	static {
		ClassPathResource pr=new ClassPathResource("fdfs-client.conf");
		try {
			ClientGlobal.init(pr.getClassLoader().getResource("fdfs-client.conf").getPath());
			trackerClient = new TrackerClient();
		} catch (IOException | MyException e) {
			log.error("error", e);
		}
	}
	
	/**
	 * 向FastDFS上传文件
	 * 
	 * @param data  文件数据
	 * @param fileExtName  文件扩展名(不包括.)
	 * @param meta  键值对数组 meta[0]数据库中Id meta[1]文件名 meta[2]上传者
	 * @return
	 */
	public static String uploadFile(byte[] data,String fileExtName,NameValuePair[] meta) {
		TrackerServer trackerServer;
		String result=null;
		try {
			trackerServer=trackerClient.getConnection();
		}catch(IOException e) {
			log.error("error",e);
			return result;
		}
		StorageClient storageClient=new StorageClient(trackerServer,null);
		try {
			String[] arr=storageClient.upload_file(data,fileExtName,meta);
			if(arr == null ||arr.length != 2)
				log.error(meta[1].getValue()+"文件,上传失败");
			else {
				log.info(meta[1].getValue()+"文件,上传成功");
				result=arr[0]+"-"+arr[1];
				System.out.println(result);
			}
		}catch(IOException | MyException  e) {
			log.error("error",e);
		}finally {
			closeTrackerServer(trackerServer);
		}
		return result;
	}

	/**
	 * 向FastDFS上传文件
	 * 
	 * @param localFileName
	 * @return 上传成功返回 组名-文件在FastDFS中的名称，上传失败返回 null
	 */
//	public static String uploadFile(String localFileName) {
//		TrackerServer trackerServer;
//		String result = null;
//		try {
//			trackerServer = trackerClient.getConnection();
//		} catch (IOException e) {
//			log.error("error", e);
//			return result;
//		}
//		StorageClient storageClient = new StorageClient(trackerServer, null);
//		try {
//			String[] arr = storageClient.upload_file(localFileName, null, null);
//			if (arr == null || arr.length != 2)
//				log.error(localFileName + "文件，上传失败");
//			else {
//				log.info(localFileName + "文件，上传成功");
//				result=arr[0]+"-"+arr[1];
//				System.out.println(result);
//			}
//		} catch (IOException | MyException e) {
//			log.error("error", e);
//		} finally {
//			closeTrackerServer(trackerServer);
//		}
//		return result;
//	}

	/**
	 * 从FastDFS下载文件
	 * 
	 * @param localFileName  本地文件名
	 * @param groupName      文件在FastDFS中的组名
	 * @param remoteFileName 文件在FastDFS中的名称
	 */
	public static void downloadFile(String localFileName, String groupName, String remoteFileName) {
		TrackerServer trackerServer;
		try {
			trackerServer = trackerClient.getConnection();
		} catch (IOException e) {
			log.error("error", e);
			return;
		}
		StorageClient storageClient = new StorageClient(trackerServer, null);
		File file = new File(localFileName);
		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
			byte[] content = storageClient.download_file(groupName, remoteFileName);
			if (content == null || content.length == 0) {
				log.error("文件大小为空！");
				boolean flag = file.delete();
				log.info("删除文件结果:{" + flag + "}");
				return;
			}
			bos.write(content);
			log.info(localFileName + "文件，下载成功");
		} catch (IOException | MyException e) {
			log.error("error", e);
		} finally {
			closeTrackerServer(trackerServer);
		}
	}

	public static void downloadFile(String localFileName,String groupName, String remoteFileName,OutputStream output) {
		TrackerServer trackerServer;
		try {
			trackerServer = trackerClient.getConnection();
		} catch (IOException e) {
			log.error("error", e);
			return;
		}
		StorageClient storageClient = new StorageClient(trackerServer, null);
		try (BufferedOutputStream bos = new BufferedOutputStream(output)) {
			byte[] content = storageClient.download_file(groupName, remoteFileName);
			if (content == null || content.length == 0) {
				log.error("文件大小为空！");
				return;
			}
			bos.write(content);
			log.info(localFileName + "文件下载成功");
		} catch (IOException | MyException e) {
			log.error("error", e);
		} finally {
			closeTrackerServer(trackerServer);
		}
	}
	
	/**
	 * 从FastDFS删除文件
	 * 
	 * @param localFileName  本地文件名
	 * @param groupName      文件在FastDFS中的组名
	 * @param remoteFileName 文件在FastDFS中的名称
	 * @return 
	 */
	public static int deleFile(String localFileName, String groupName, String remoteFileName) {
		int i=1;
		TrackerServer trackerServer;
		try {
			trackerServer = trackerClient.getConnection();
		} catch (IOException e) {
			log.error("error", e);
			return i;
		}
		StorageClient storageClient = new StorageClient(trackerServer, null);
		try {
			i = storageClient.delete_file(groupName, remoteFileName);
			if (i == 0)
				log.info(localFileName + "文件，删除成功");
			else
				log.info(localFileName + "文件，删除失败");
		} catch (IOException | MyException e) {
			log.error("error", e);
		} finally {
			closeTrackerServer(trackerServer);
		}
		return i;
	}

	/**
	 * 将uploadFile方法的返回结果切割为组名和FastDFS的文件名
	 * 
	 * @param result arr[0] 组名 arr[1] 文件名
	 * @return
	 */
	public static String[] separationResult(String result) {
		String[] arr=null;
		if(result!=null)
			arr= result.split("-",2);
		else
			System.out.println("传入参数为空");
		return arr;
	}

	private static void closeTrackerServer(TrackerServer trackerServer) {
		try {
			if (trackerServer != null) {
				log.info("关闭trackerServer连接");
				trackerServer.close();
			}
		} catch (IOException e) {
			log.error("error", e);
		}

	}
}
