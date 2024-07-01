package com.core.events.origin;

import com.constant.Constant;
import com.common.abstracts.CustomEvent;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.event.InputEvent;

@Slf4j
public class MouseRightClickEvent implements CustomEvent {

    private double aimX;
    private double aimY;
    private int delay;

    public MouseRightClickEvent(Double x, Double y, int delay) {
        this.aimX = x;
        this.aimY = y;
        this.delay = delay;
    }

    @Override
    public void execute(Robot robot) throws InterruptedException{
        try{
            robot.delay(delay);
            robot.mousePress(InputEvent.BUTTON3_MASK);
            robot.delay(Constant.EVENT_DELAY_AFTER_EVENT);
        }catch(Exception e){
            log.error("代理移动鼠标异常", e);
        }
    }
}
