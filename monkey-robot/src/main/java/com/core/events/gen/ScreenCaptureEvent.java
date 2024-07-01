package com.core.events.gen;

import com.constant.Constant;
import com.core.utils.ImageUtil;
import lombok.Data;
import com.common.abstracts.CustomEvent;
import com.core.exception.MessageAlertException;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
@Data
public class ScreenCaptureEvent implements CustomEvent {
    int tryTimes = Constant.SCREEN_CAPTURE_EVENT_RETRY_TIMES;
    //
    private int leftx;
    private int lefty;
    private int width;
    private int height;
    //
    private int delay;
    private String tempImage;

    public ScreenCaptureEvent(int delay) {
        this.delay = delay;
    }

    public ScreenCaptureEvent(int leftx, int lefty, int width, int height, int delay, String tempImage) {
        this.leftx = leftx;
        this.lefty = lefty;
        this.width = width;
        this.height = height;
        this.delay = delay;
        this.tempImage = tempImage;
    }

    @Override
    public void execute(Robot robot) throws InterruptedException {
        // 这里会截图，并且识别截图内容是否符合目标
        // 如果不符合就要抛异常
        robot.delay(delay);

        boolean res = imageMatch(robot);
        if(! res) {
            throw new MessageAlertException("界面识别超时，确认是否功能加载过慢？");
        }
    }

    private boolean imageMatch(Robot robot) {
        for (int i = 0; i < tryTimes; i++) {
            log.info("第 {} 次抓图识别中...", i);
            Rectangle screenRect = new Rectangle();
            screenRect.setRect((double) leftx, (double) lefty, (double) width, (double) height);
            BufferedImage screenCapture = robot.createScreenCapture(screenRect);

            File tmpFile = new File(tempImage);
            String aimaim = tmpFile.getName().split("\\.")[0];
            log.info("aimaim {}", aimaim);
            File file = new File(ImageUtil.SCREEN_SHOT_PATH + "/" + aimaim + i + ".png");
            file.mkdirs();
            try {
                ImageIO.write(screenCapture, "png", file);
            } catch (IOException e) {
                log.error("robot screen short write to desk error", e);
            }
            // send http to image match AI
            boolean recognize = recognize(screenCapture);
            if(recognize) {
                return true;
            }
            robot.delay(Constant.Event.SCREEN_CAPTURE_MATCH); // 每次尝试图片识别间隔时间
        }

        return false;
    }

    private boolean recognize(BufferedImage screenCapture) {
        BufferedImage temp = ImageUtil.readImage(tempImage);
        boolean compare = ImageUtil.compare(screenCapture, temp, width, height);
        // 或者用JAVA 做个位图计算
        return compare;
    }
}
