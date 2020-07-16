package com.baizhi.controller.back;

import com.baizhi.entity.Video;
import com.baizhi.service.back.VideoService;
import com.baizhi.util.UploadVideoAndVideoCover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("backVideo")
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 分页查询所有数据
     * @param rows
     * @param page
     * @return
     */
    @RequestMapping("queryAllVideoByPage")
    @ResponseBody
    public Map<String,Object> queryAllVideoByPage(Integer rows, Integer page){
        Map<String,Object> map = videoService.queryAllVideoByPage(rows,page);
        return map;
    }


    /**
     * 视屏上传 截取视频封面
     */
    @ResponseBody
    @RequestMapping("videoUpload")
    public String videoUpload(String title,String categoryId, String brief, MultipartFile file) {

        /*
        * 调用截取视屏某一帧为视屏封面
        * 上传视频和视屏封面到阿里云OSS的工具类
        *
        * 返回值：
        * 视屏封面的网络路径: key值imageNetworkPath
        * 上传视屏的网络路径: key值videoNetworkPath
        *
        * 参数：上传的视频文件
        * */
        HashMap<String, String> map = UploadVideoAndVideoCover.videoUpload(file);
        //取出封面路径
        String coverPath = map.get("imageNetworkPath");
        //取出视屏路径
        String videoPath = map.get("videoNetworkPath");
        //调用业务方法存储到数据库
        videoService.addVideo(title,categoryId, brief,coverPath,videoPath);
        return "上传成功";

    }


    /**
     * 修改视屏信息
     * @param video
     * @param coverFile
     * @param videoFile
     * @return
     */
    @RequestMapping("updateVideo")
    @ResponseBody
    public Map<String,Object> updateVideo(Video video,MultipartFile coverFile,MultipartFile videoFile){
        HashMap<String, Object> map1 = new HashMap<>();
        Map<String,Object> map = videoService.UpdateVideo(video,coverFile,videoFile);
        map1.put("error","还没有做");
        map1.put("012","没有");
        return map1;
    }


    @RequestMapping("deleteVideo")
    @ResponseBody
    public void deleteVideo(Video video){
        videoService.deleteVideo(video);
    }
}
