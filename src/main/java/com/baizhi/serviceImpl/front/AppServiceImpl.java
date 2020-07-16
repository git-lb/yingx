package com.baizhi.serviceImpl.front;
import com.baizhi.common.CommonResult;
import com.baizhi.dao.AppMapper;
import com.baizhi.dao.VideoMapper;
import com.baizhi.entity.Video;
import com.baizhi.po.VideoPO;
import com.baizhi.service.front.AppService;
import com.baizhi.util.*;
import com.baizhi.vo.VideoVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class AppServiceImpl implements AppService {

    @Resource
    private AppMapper appMapper;

    @Resource
    private VideoMapper videoMapper;

    /**
     * 业务层：发送验证码
     * @param phone:手机号 String
     * @return ：CommonResult  公共返回对象  包含data status message
     */
    public CommonResult getPhoneCode(String phone) {
        //调用生成随机数的工具类
        String numberCode = SendSmsCode.getNumberCode(4);

        //调用发送验证码的工具类
        String messageResult = SendSmsCode.sendSmsCodeToPhone(phone, numberCode);

        //判断发送是否成功
        if(messageResult.equals("执行成功")){
            //返回成功信息
             return new CommonResult(phone,"验证码发送成功","100");
        }else{
            //返回失败信息
            return new CommonResult(null,"验证码发送失败","104");
        }
    }


    /**
     * 页展示视频:根据时间排序查询所有视频数据
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public CommonResult queryByReleaseTime() {

        try {

            //调用模型层查询视频数据
            List<VideoPO> list = appMapper.queryByReleaseTime();

            //redis取出视频点赞数  //这里做一个模仿 未集成redis
            String like = "10";

            List<VideoVO> videoVOList = new ArrayList<>();

            //前端页面展示数据  封装到VO
            for (VideoPO videoPO : list) {
                VideoVO videoVO = new VideoVO();
                videoVO.setCateName(videoPO.getCateName());
                videoVO.setCover(videoPO.getCoverPath());
                videoVO.setDescription(videoPO.getVBrief());
                videoVO.setId(videoPO.getVId());
                videoVO.setLikeCount(like);
                videoVO.setUserPhoto(videoPO.getHeadImg());
                videoVO.setUploadTime(videoPO.getCreateDate());
                videoVO.setPath(videoPO.getVideoPath());
                videoVO.setVideoTitle(videoPO.getVTitle());
                videoVOList.add(videoVO);
            }

            //无异常查询成功 返回查询数据
            return new CommonResult(videoVOList,"查询成功","100");

        }catch (Exception e){

            //打印异常信息
            e.printStackTrace();

            //返回查询结果
            return new CommonResult(null,"查询失败","104");
        }
    }


    /**
     * 用户发布视频
     * @param description
     * @param videoTitle
     * @param categoryId
     * @param userId
     * @param videoFile
     * @return
     */
    @Override
    public CommonResult save(String description, String videoTitle, String categoryId, String userId, MultipartFile videoFile) {


        try {
            //获取上传视频名字
            String videoName = videoFile.getOriginalFilename();

            //获取上传视频byte数组
            byte[] bytes = null;
            try {
                bytes = videoFile.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //上传视频
            String videoPath = AliyunBucketOSSUtil.fileByteUpload("video", videoName, bytes);
            Date date = new Date();
            //将视频文件转成File类型
            File file = MultipartFileToFile.multipartFileToFile(videoFile);

            //获取视频封面
            BufferedImage bufferedImage = CutVideoImage.cutVideoImage(file);

            //将视频封面转换成byte数组
            byte[] imageBytes = BufferedImageToBytes.ImageToBytes(bufferedImage);

            //定义图片名字
            String coverVideoImage = videoName.substring(0,videoName.indexOf("."));

            String imageName = coverVideoImage+".jpg";

            //上传视频封面
            String photo = AliyunBucketOSSUtil.fileByteUpload("photo", imageName, imageBytes);

            //数据入库
            Video video = new Video();
            video.setCoverPath(photo);
            video.setCategoryId(categoryId);
            video.setBrief(description);
            video.setTitle(videoTitle);
            video.setUserId(userId);
            video.setId(UUIDUtil.getUUID());
            video.setVideoPath(videoPath);
            video.setCreateDate(date);
            videoMapper.insertSelective(video);

            return new CommonResult(null,"添加成功","100");
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult(null,"添加失败","104");
        }
    }

    /**
     *查询视频详情
     * @param videoId
     * @param cateId
     * @param userId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public CommonResult queryByVideoDetail(String videoId, String cateId, String userId) {
        //List<videoDetailPO> list = appMapper.queryByVideoDetail(videoId,cateId,userId);

        return null;
    }
}