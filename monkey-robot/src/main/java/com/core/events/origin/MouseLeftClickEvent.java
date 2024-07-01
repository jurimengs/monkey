package com.core.events.origin;

import com.common.abstracts.CustomEvent;

import java.awt.*;
import java.awt.event.InputEvent;

public class MouseLeftClickEvent implements CustomEvent {

    private double aimX;
    private double aimY;
    private int delay;

    public MouseLeftClickEvent(Double x, Double y, int delay) {
        this.aimX = x;
        this.aimY = y;
        this.delay = delay;
    }

    @Override
    public void execute(Robot robot) throws InterruptedException{
        robot.delay(delay);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(30); // 模拟点击的间隙
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(3000); // 点击后的间隙
    }
}
