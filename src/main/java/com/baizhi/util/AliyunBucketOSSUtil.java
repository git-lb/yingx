package com.baizhi.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.File;

//阿里云对象bucketOSS工具类
public class AliyunBucketOSSUtil {
    private static String endpoint = "http://oss-cn-qingdao.aliyuncs.com";  //地域节点
    private static  String accessKeyId = "LTAI4G7kj7Hv38nYdySmeMYk";  //用户的AccessKey ID
    private static String accessKeySecret = "vjJR27b9AKPYnQX2OdTD7UWbXE06lm";  //用户的AccessKey Secret
    private static String bucketName = "yingx-liub";  //上传的bucket名字


    /**
     * 文件byte[]上传
     * 参数：
     *  fileByte:上传文件转化的byte数组
     *  fileName:上传到bucket的文件名字  拼接文件夹
     *  catalogue : bucket的目录
     */
    public static String fileByteUpload(String catalogue,String fileName , byte[] content){
        String newFileName = null;
        //判断上传文件名字是否有空格
        if(fileName.contains(" ")){
            newFileName = fileName.replace(" ", "");
        }else {
            newFileName = fileName;
        }

        long l = System.currentTimeMillis();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);
        // 上传Byte数组。
        ossClient.putObject(bucketName, catalogue+"/"+l+"-"+newFileName, new ByteArrayInputStream(content));
        // 关闭OSSClient。
        ossClient.shutdown();


        //返回网络地址  给文件名拼接时间戳
        String networkFileName = "https://yingx-liub.oss-cn-qingdao.aliyuncs.com/"+catalogue+"/"+l+"-"+newFileName;
        return networkFileName;
    }


    /**
     * 删除文件
     * @param fileName：上传到bucket的文件名字  拼接文件夹
     */
    public static void deleteFile(String fileName){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, fileName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 下载文件
     */
    public static void downloadFile(String fileName,String downloadPath){
        //<yourObjectName>表示从OSS下载文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        //String fileName = "userHeadImg/1594266510664-timgOBS8CQSB.jpg";
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
// 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, fileName), new File(downloadPath));
// 关闭OSSClient。
        ossClient.shutdown();
    }

}
