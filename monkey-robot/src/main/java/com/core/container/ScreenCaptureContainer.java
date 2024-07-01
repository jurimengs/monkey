package com.core.container;

import com.core.utils.ImageUtil;

import java.awt.image.BufferedImage;

public class ScreenCaptureContainer {
    // TODO 这个值需要在流程里面设计。比如截一张图，然后设置进来
    public static BufferedImage WORK_CONSOLE = ImageUtil.readImage(ScreenCaptureContainer.TEMP_PATH + "/workconsole.png");
    public static String TEMP_PATH = "f:/robot-screentshot/temp";
}
