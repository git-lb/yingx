package com.baizhi.serviceImpl.back;

import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.AddLog;
import com.baizhi.annotation.DelCache;
import com.baizhi.dao.VideoMapper;
import com.baizhi.entity.Video;
import com.baizhi.entity.VideoExample;
import com.baizhi.service.back.VideoService;
import com.baizhi.util.AliyunBucketOSSUtil;
import com.baizhi.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;


    /**
     * 删除视频
     * @param video
     */
    @DelCache
    @AddLog("删除视频")
    public void deleteVideo(Video video) {
        //删除视屏封面图
        String coverPath = video.getCoverPath();
        //截取字符串
        String[] coverPathSplit = coverPath.split("m/");
        /*
        * 参数：目录+文件名的字符串
        * */
        AliyunBucketOSSUtil.deleteFile(coverPathSplit[1]);

        //删除视频
        String videoPath = video.getVideoPath();
        //截取字符串
        String[] videoPathSplit = videoPath.split("m/");
        /*
         * 参数：目录+文件名的字符串
         * */
        AliyunBucketOSSUtil.deleteFile(videoPathSplit[1]);

        //删除数据库信息根据id
        videoMapper.deleteByPrimaryKey(video.getId());

    }

    /**
     *  视频修改
     * @param video ：视频的信息
     * @param coverFile ：修改的封面
     * @param videoFile ：修改的视频
     * @return
     */
    @DelCache
    @AddLog("编辑视频")
    public Map<String, Object> UpdateVideo(Video video, MultipartFile coverFile, MultipartFile videoFile) {
        //判断视频或图片是否被修改

        if(coverFile != null && videoFile == null){
            //1.图片被修改 视频未被修改
            try {
                //1.上传图片到阿里云的OSS存储
                String coverImage = coverFile.getOriginalFilename();
                byte[] bytes = coverFile.getBytes();
                /**
                 * 上传的参数
                 * coverFileName：拼接过bucket目录的图片名字
                 * bytes：图片文件的bytes数组
                 * imageNetwork :图片的网络地址
                 */
                String imageNetwork = AliyunBucketOSSUtil.fileByteUpload("photo", coverImage, bytes);
                //oldCoverPath  https://yingx-liub.oss-cn-qingdao.aliyuncs.com/photo/1594037013573-刘亦菲-自己-《花木兰》电影中文主题曲.jpg
                //2.将原来视屏的封面删除
                String oldCoverPath = video.getCoverPath();
                //截取文件名字
                String[] split = oldCoverPath.split("m/");

                /**
                 * 上传的参数
                 * split[1])：拼接过bucket目录的图片名字
                 */
                AliyunBucketOSSUtil.deleteFile(split[1]);

                video.setCoverPath(imageNetwork);
                VideoExample videoExample = new VideoExample();
                videoExample.createCriteria().andIdEqualTo(video.getId());
                //4.修改数据库信息
                videoMapper.updateByExampleSelective(video,videoExample);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(videoFile != null && coverFile == null){
            //2.视屏被修改 图片未被修改
            try {
                //1.上传视频到阿里云的OSS存储
                String videoName = videoFile.getOriginalFilename();
                byte[] bytes = videoFile.getBytes();
                /**
                 * 上传的参数
                 * coverFileName：拼接过bucket目录的视频名字
                 * bytes：视频文件的bytes数组
                 */
                String video1 = AliyunBucketOSSUtil.fileByteUpload("video", videoName, bytes);
                //oldCoverPath  https://yingx-liub.oss-cn-qingdao.aliyuncs.com/photo/1594037013573-刘亦菲-自己-《花木兰》电影中文主题曲.jpg
                //2.将原来视屏删除
                String oldVideoPath = video.getVideoPath();
                //截取文件名字
                String[] split = oldVideoPath.split("m/");

                /**
                 * 上传的参数
                 * split[1])：拼接过bucket目录的视频名字
                 */
                AliyunBucketOSSUtil.deleteFile(split[1]);

                //3.修改文件信息
                video.setCoverPath(video1);
                VideoExample videoExample = new VideoExample();
                videoExample.createCriteria().andIdEqualTo(video.getId());
                //4.修改数据库信息
                videoMapper.updateByExampleSelective(video,videoExample);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(coverFile != null && videoFile != null){
            //3.视屏图片都被修改
            try {
                //图片操作
                //1.上传图片到阿里云的OSS存储
                String coverImage = coverFile.getOriginalFilename();
                byte[] imgbytes = coverFile.getBytes();
                /**
                 * 上传的参数
                 * coverFileName：拼接过bucket目录的图片名字
                 * bytes：图片文件的bytes数组
                 */
                String photo = AliyunBucketOSSUtil.fileByteUpload("photo", coverImage, imgbytes);
                //oldCoverPath  https://yingx-liub.oss-cn-qingdao.aliyuncs.com/photo/1594037013573-刘亦菲-自己-《花木兰》电影中文主题曲.jpg
                //2.将原来视屏的封面删除
                String oldCoverPath = video.getCoverPath();
                //截取文件名字
                String[] split = oldCoverPath.split("m/");

                /**
                 * 上传的参数
                 * split[1])：拼接过bucket目录的图片名字
                 */
                AliyunBucketOSSUtil.deleteFile(split[1]);

                //视频操作
                //1.上传视频到阿里云的OSS存储
                String newVideoFileName = videoFile.getOriginalFilename();
                byte[] bytes = videoFile.getBytes();
                /**
                 * 上传的参数
                 * coverFileName：拼接过bucket目录的视频名字
                 * bytes：视频文件的bytes数组
                 */
                String video1 = AliyunBucketOSSUtil.fileByteUpload("video", newVideoFileName, bytes);
                //oldCoverPath  https://yingx-liub.oss-cn-qingdao.aliyuncs.com/photo/1594037013573-刘亦菲-自己-《花木兰》电影中文主题曲.jpg
                //2.将原来视屏删除
                String oldVideoPath = video.getVideoPath();
                //截取文件名字
                String[] spli = oldVideoPath.split("m/");

                /**
                 * 上传的参数
                 * split[1])：拼接过bucket目录的视频名字
                 */
                AliyunBucketOSSUtil.deleteFile(spli[1]);

                video.setCoverPath(photo);
                video.setVideoPath(video1);
                VideoExample videoExample = new VideoExample();
                videoExample.createCriteria().andIdEqualTo(video.getId());
                //4.修改数据库信息
                videoMapper.updateByExampleSelective(video,videoExample);
            }catch (Exception e){
                e.printStackTrace();
            }

        }else if(coverFile == null && videoFile == null){
            //4.视屏图片都没修改
            VideoExample videoExample = new VideoExample();
            videoExample.createCriteria().andIdEqualTo(video.getId());
            videoMapper.updateByExampleSelective(video,videoExample);

        }

        return null;
    }


    /**
     * 查询二级类别下的视频信息
     * @param id
     * @return
     */
    @AddCache
    @Transactional(propagation = Propagation.SUPPORTS)
    public HashMap<String, String> queryVideoByCateId(String id) {

        HashMap<String, String> map = new HashMap<>();

        //查询此类别id下是否有视频
        VideoExample videoExample = new VideoExample();
        videoExample.createCriteria().andCategoryIdEqualTo(id);
        List<Video> list = videoMapper.selectByExample(videoExample);

        //判断list集合
        if(list.size() != 0){
            //类别下有视频
            map.put("status","2");  //状态==2不能删除
        }else{
            //类别下没有视频
            map.put("status","1"); //==1可以删除
        }
        return map;
    }


    /**
     * @apiNote 分页查询视频信息展示数据
     * @param rows
     * @param page
     * @return
     */
    @AddCache
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String,Object> queryAllVideoByPage(Integer rows, Integer page) {

        HashMap<String, Object> map = new HashMap<>();
        Video video = new Video();

        //查询视频总条数
        int counts = videoMapper.selectCount(video);

        //计算总页数
        Integer total = counts%rows == 0?counts/rows:counts/rows+1;

        //分页查询
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Video> list = videoMapper.selectByRowBounds(video, rowBounds);

        //返回数据
        map.put("page",page);  //当前页数
        map.put("list",list);  //查询的数据
        map.put("records",counts); //数据总条数
        map.put("total",total);   //数据总页数

        return map;
    }


    /**
     * 添加视屏
     * @param title
     * @param brief
     * @param coverPath
     * @param videoPath
     */
    @AddLog("添加视屏")
    @DelCache
    public void addVideo(String title, String categoryId,String brief, String coverPath, String videoPath) {
        Video video = new Video();
        video.setBrief(brief);
        video.setId(UUIDUtil.getUUID());
        video.setCoverPath(coverPath);
        video.setVideoPath(videoPath);
        video.setCreateDate(new Date());
        video.setTitle(title);
        video.setCategoryId(categoryId);
        videoMapper.insertSelective(video);

    }


}
