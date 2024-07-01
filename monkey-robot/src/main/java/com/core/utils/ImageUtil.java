package com.core.utils;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
public class ImageUtil {
    public static String SCREEN_SHOT_PATH = "f:/robot-screentshot/robot";

    public static boolean compare(BufferedImage imga, BufferedImage imgb, int imageWidth, int imageHeight) {
        int[] dataA = getData(imga, imageWidth, imageHeight);
        int[] dataB = getData(imgb, imageWidth, imageHeight);
        float percent = compare(dataA, dataB);
        log.info("识别相似度： {}", percent);
        if(percent > 60) {
            return true;
        }
        return false;
    }

    public static float compare(int[] s, int[] t) {
        try{
            float result = 0F;
            for (int i = 0; i < 256; i++) {
                int abs = Math.abs(s[i] - t[i]);
                int max = Math.max(s[i], t[i]);
                result += (1 - ((float) abs / (max == 0 ? 1 : max)));
            }
            return (result / 256) * 100;
        }catch(Exception exception){
            return 0;
        }
    }

    public static BufferedImage readImage(String name) {
        try {
            return ImageIO.read(new File(name));
        } catch (IOException e) {
            log.error("读图片文件异常", e);
            return null;
        }
    }

    public static int[] getData(BufferedImage img, int imageWidth, int imageHeight) {
        try{
//            BufferedImage img = ImageIO.read(new File(name));
            BufferedImage slt = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
            slt.getGraphics().drawImage(img, 0, 0, imageWidth, imageHeight, null);
            // ImageIO.write(slt,"jpeg",new File("slt.jpg"));
            int[] data = new int[256];
            for (int x = 0; x < slt.getWidth(); x++) {
                for (int y = 0; y < slt.getHeight(); y++) {
                    int rgb = slt.getRGB(x, y);
                    Color myColor = new Color(rgb);
                    int r = myColor.getRed();
                    int g = myColor.getGreen();
                    int b = myColor.getBlue();
                    data[(r + g + b) / 3]++;
                }
            }
            // data 就是所谓图形学当中的直方图的概念
            return data;
        }catch(Exception e ){
            log.error("文件没有找到", e);
            return null;
        }
    }

}
