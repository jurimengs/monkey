package com.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
public class ImageUtil {

    public static BufferedImage loadImage(String file) {

        try {
            return ImageIO.read(new File(file));
        } catch (IOException e) {
            log.error("读图片文件异常", e);
            return null;
        }
    }

}
