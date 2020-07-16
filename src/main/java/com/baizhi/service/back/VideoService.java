package com.baizhi.service.back;

import com.baizhi.entity.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

public interface VideoService {

    /**
     * 查询二级类别下的视频信息
     * @param id
     * @return
     */
    HashMap<String, String> queryVideoByCateId(String id);

    /**
     * 分页查询视频信息
     * @param rows
     * @param page
     * @return
     */
    Map<String,Object> queryAllVideoByPage(Integer rows, Integer page);

    /**
     * 将视频信息添加到数据库
     * @param title
     * @param brief
     * @param coverPath
     * @param videoPath
     */
    void addVideo(String title,String categoryId, String brief, String coverPath, String videoPath);

    /**
     * 修改视频信息
     * @param video ：视频的信息
     * @param coverFile ：修改的封面
     * @param videoFile ：修改的视频
     * @return
     */
    Map<String, Object> UpdateVideo(Video video, MultipartFile coverFile, MultipartFile videoFile);

    /**
     * 删除存储视频和封面 删除数据库信息
     * @param video
     */
    void deleteVideo(Video video);
}
