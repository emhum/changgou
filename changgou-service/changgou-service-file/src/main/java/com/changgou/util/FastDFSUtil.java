package com.changgou.util;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

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
            String filenmae = new ClassPathResource("fdfs_client.conf").getPath();
            ClientGlobal.init(filenmae);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}





















