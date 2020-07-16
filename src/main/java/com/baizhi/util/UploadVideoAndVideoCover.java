package com.baizhi.util;
import org.springframework.web.multipart.MultipartFile;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class UploadVideoAndVideoCover {

    /**
     *      方法内容：
     *    截取视屏的某一帧为封面
     *   上传视屏和视屏封面到阿里云OSS存储
     *
     * @param myFile：上传的视频文件
     * @return 返回视屏和封面的网络访问路径
     */



    public static HashMap<String, String> videoUpload(MultipartFile myFile){

//上传视频
        byte[] videoBytes = null;
        //获取上传文件的字节数组  字节流上传
        try {
            videoBytes = myFile.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取上传文件的名字
        String fileName = myFile.getOriginalFilename();

        //上传到bucket的video文件下
        String videoNetwork = AliyunBucketOSSUtil.fileByteUpload("video", fileName, videoBytes);

//截取封面 上传图片
        //获取MultipartFile的文件的File类型
        File VideoTypeFile = MultipartFileToFile.multipartFileToFile(myFile);
        // 截取视频中图片 调用工具类 参数为File类型的视频文件
        BufferedImage bufferedImage = CutVideoImage.cutVideoImage(VideoTypeFile);

        //将获取的缓冲区图像类转换成字节数组
        byte[] imageBtye = BufferedImageToBytes.ImageToBytes(bufferedImage);

        //截取视频的名字 用于给图片定义名字
        String firstName = fileName.substring(0, fileName.indexOf("."));
        //给图片定义名字
        String imageName = firstName+".jpg";
        //上传图片到bucket下的photo
        String imageNetwork = AliyunBucketOSSUtil.fileByteUpload("photo", imageName, imageBtye);

        //删除将MultipartFile类型视屏文件转换成File类型时上传的视频文件
        VideoTypeFile.delete();

        //返回上传视频和封面的网络路径

        //返回数据
        HashMap<String, String> map = new HashMap<>();
        map.put("imageNetworkPath",imageNetwork);
        map.put("videoNetworkPath",videoNetwork);

        return map;


    }
}
