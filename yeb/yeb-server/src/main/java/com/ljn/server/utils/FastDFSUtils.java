package com.ljn.server.utils;

import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: ljn
 * @Date: 2022/03/01/19:24
 * @Description:FastDFS工具类
 */
public class FastDFSUtils {
    private static Logger logger = LoggerFactory.getLogger(FastDFSUtils.class);

    //初始化客户端
    static {
        try{
            String filePath = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();
            ClientGlobal.init(filePath);
        }catch (Exception e){
            logger.error("初始化FastDFS失败",e);
        }
    }
    //上传文件
    public static String[] upload(MultipartFile file){
        String name = file.getOriginalFilename();
        logger.info("文件名",name);
        StorageClient storageClient = null;
        String[] uploadResults = null;
        try {
            storageClient = getStorageClient();
            uploadResults = storageClient.upload_file(file.getBytes(),name.substring(name.lastIndexOf(".")+1),null);
        }catch (Exception e){
            logger.error("上传失败",e.getMessage());
        }
        if(null==uploadResults){
            logger.error("上传失败");
        }
        return uploadResults;
    }

    //获取文件信息
    public static FileInfo getFileInfo(String groupName,String remoteFileName){
        StorageClient storageClient = null;
        try {
            storageClient = getStorageClient();
            return  storageClient.get_file_info(groupName,remoteFileName);
        }catch (Exception e){
            logger.error("文件信息读取失败",e.getMessage());
        }
        return null;
    }
    //下载
    public static InputStream downFile(String groupName, String remoteFileName){
        StorageClient storageClient = null;
        try {
            storageClient = getStorageClient();
            byte[] bytes = storageClient.download_file(groupName,remoteFileName);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            return inputStream;
        }catch (Exception e){
            logger.error("文件下载失败",e.getMessage());
        }
        return null;
    }

    //删除文件
    public static void deleteFile(String groupName,String remoteFileName){
        StorageClient storageClient = null;
        try {
            storageClient = getStorageClient();
            storageClient.delete_file(groupName,remoteFileName);
        }catch (Exception e){
            logger.error("文件删除失败",e.getMessage());
        }
    }
    //获取文件路径
    public static String getTrackerUrl(){
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        try {
            trackerServer = trackerClient.getTrackerServer();
            storageServer = trackerClient.getStoreStorage(trackerServer);
        }catch (Exception e){
            logger.error("文件路径获取失败",e.getMessage());
        }
        return "http://"+storageServer.getInetSocketAddress().getHostString()+":8888/";
    }

    private static TrackerServer getTrackerServer()throws IOException{
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getTrackerServer();
        return trackerServer;
    }
    //生成storage客户端
    private static StorageClient getStorageClient()throws IOException {
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient = new StorageClient(trackerServer,null);
        return storageClient;
    }

}
