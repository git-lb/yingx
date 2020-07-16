package com.baizhi.util;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CutVideoImage {

    /**
     *   方法作用：截取视屏的某一帧生成图片
     *
     * @param videoFile：File类型的上传视屏文件
     *
     * @return
     *          BufferedImage的认知：
     * Image是一个抽象类，BufferedImage是其实现类，是一个带缓冲区图像类.
     * 主要作用是将一幅图片加载到内存中
     * （BufferedImage生成的图片在内存里有一个图像缓冲区，利用这个缓冲区我们可以很方便地操作这个图片），
     * 提供获得绘图对象、图像缩放、选择图像平滑度等功能，通常用来做图片大小变换、图片变灰、设置透明不透明等。
     */

    public static BufferedImage cutVideoImage(File videoFile){
        FFmpegFrameGrabber ff = null;
        try {
            ff = new FFmpegFrameGrabber(videoFile);
            ff.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int i = 0;
        Frame frame = null;
        int lenght = ff.getLengthInFrames()/2;
        while (i < lenght) {// 选取帧数
            try {
                frame = ff.grabFrame();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
            if (frame.image != null) break;
            i++;
        }

        Java2DFrameConverter converter =new Java2DFrameConverter();
        //获取到带缓冲区图像类的图片
        BufferedImage srcBi =converter.getBufferedImage(frame);
        try {
            ff.stop();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
        //ImageIO.write(srcBi, "jpg", targetFile);  上传操作
        //返回截取到的图片BufferedImage
        return srcBi;

    }
}
