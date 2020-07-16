package com.baizhi.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BufferedImageToBytes {


    /**
     * 将BufferedImage类型的文件转换成bytes数组类型
     * @param bImage
     * @return
     */
    public static byte[] ImageToBytes(BufferedImage bImage) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
}
