package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;


/**
 * 实现FastDFS文件管理
 *      文件上传
 *      文件删除
 *      文件下载
 *      文件信息获取
 *      Storage信息获取
 *      Tracker信息获取
 */
public class FastDFSUtil {
    /**
     * 加载Tracker连接信息
     */
    static {
        try {
            String filename = new ClassPathResource("fdfs_client.conf").getPath();
            ClientGlobal.init(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     * @param fastDFSFile
     * @return
     * @throws Exception
     */
    public static String[] upload(FastDFSFile fastDFSFile) throws Exception {
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author",fastDFSFile.getAuthor());
        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();

        //通过TrackerServer的连接信息可以获取Storage连接信息，创建StorageClient对象存储Storage的连接信息
        StorageClient storageClient = new StorageClient(trackerServer,null);

        //通过StorageClient访问Storage，实现文件上传，并且获取文件上传后的存储信息
        /**
         * 1:上传文件的字节数组
         * 2:文件的扩展名
         * 3:附加参数
         *
         */
        String[] uploads = storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), meta_list);
        System.out.println(uploads[0]);
        System.out.println(uploads[1]);
        return uploads;
    }

    /**
     * 获取文件信息
     * @param groupName：文件的组名，group1
     * @param remoteFileName：文件的存储路径名字 M00/00/00/wKgUyV8cAByAUHXbAAAeF9Hr_xE814.png
     */
    public static FileInfo getFile(String groupName, String remoteFileName) throws Exception {
        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();

        StorageClient storageClient = new StorageClient(trackerServer,null);
        FileInfo file_info = storageClient.get_file_info(groupName, remoteFileName);

        return file_info;
    }

    /**
     * 文件下载
     * @param groupName
     * @param remoteFileName
     * @throws Exception
     */
    public static InputStream downloadFile(String groupName, String remoteFileName) throws Exception {

        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient = new StorageClient(trackerServer,null);
        //文件下载
        byte[] buffer = storageClient.download_file(groupName, remoteFileName);
        return new ByteArrayInputStream(buffer);
    }

    /**
     * 删除文件
     * @param groupName
     * @param remoteFileName
     * @throws Exception
     */
    public static void deleteFile(String groupName,String remoteFileName) throws Exception {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageClient storageClient = new StorageClient(trackerServer,null);
        //删除文件
        storageClient.delete_file(groupName,remoteFileName);
    }

    public static StorageServer getStorages() throws Exception {
        //创建trackerClient对象，通过trackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取Storage信息
        StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
        return storeStorage;
    }

    /**
     * 获取Storage的IP和端口信息
     * @return
     */
    public static ServerInfo[] getServerInfo(String groupName, String remoteFileName) throws Exception {
        //创建trackerClient对象，通过trackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer的链接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取Storage的IP和端口信息
       return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
    }

    public static String getTrackerInfo() throws Exception {
        //创建TrackerClient客户端对象
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient对象获取TrackerServer信息
        TrackerServer trackerServer = trackerClient.getConnection();

        //Tracker的IP，HTTP端口
        String ip = trackerServer.getInetSocketAddress().getHostString();
        int tracker_http_port = ClientGlobal.getG_tracker_http_port(); //8080
        String url = "http://" + ip + ":" + tracker_http_port;
        return url;

    }

    /**
     * 获取Tracker
     * @return
     * @throws Exception
     */
    public static TrackerServer getTrackerServer() throws Exception {
        //创建TrackerClient客户端对象
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient对象获取TrackerServer信息
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerServer;
    }

    public static void main(String[] args) throws Exception {
//        FileInfo fileInfo = getFiles("group1", "M00/00/00/wKgUyV8cAByAUHXbAAAeF9Hr_xE814.png");
//        System.out.println(fileInfo.getFileSize());
//        System.out.println(fileInfo.getSourceIpAddr());
        //文件下载
//        InputStream is = downloadFile("group1", "M00/00/00/wKgUyV8cAByAUHXbAAAeF9Hr_xE814.png");
//        //将文件写入到本地磁盘
//        FileOutputStream os = new FileOutputStream("c:/test/1.jpg");
//        //定义一个缓冲区
//        byte[] buffer = new byte[1024];
//        while (is.read(buffer)!=-1){
//            os.write(buffer);
//        }
//        os.flush();
//        is.close();

        //获取storage信息
//        StorageServer storageServer = getStorages();
//        System.out.println(storageServer.getStorePathIndex());
//        System.out.println(storageServer.getInetSocketAddress().getHostString());

        //获取Storage的IP和端口信息
//        ServerInfo[] groups = getServerInfo("group1", "M00/00/00/wKgUyV8cAByAUHXbAAAeF9Hr_xE814.png");
//        for (ServerInfo group : groups) {
//            System.out.println(group.getIpAddr());
//            System.out.println(group.getPort());
//
//        }

        System.out.println(getTrackerInfo());

    }

}





















