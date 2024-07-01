package com.core.events.gen;

import com.constant.Constant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import com.common.abstracts.CustomEvent;

import java.awt.*;
import java.awt.event.InputEvent;

@Slf4j
@Data
public class GenMouseRightClickEvent implements CustomEvent {
    private GenMouseMoveEvent mouseMoveEvent; // 可以将移动事件与点击整合

    private int delay;

    public GenMouseRightClickEvent(int delay) {
        this.delay = delay;
    }

    public GenMouseRightClickEvent(int delay, GenMouseMoveEvent mouseMoveEvent) {
        this.delay = delay;
        this.mouseMoveEvent = mouseMoveEvent;
    }

    @Override
    public void execute(Robot robot) throws InterruptedException{

        if(mouseMoveEvent != null) {
            mouseMoveEvent.execute(robot);
        }
        robot.delay(delay);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(Constant.EVENT_DELAY_AFTER_EVENT); // 模拟点击的间隙
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(Constant.EVENT_DELAY_AFTER_EVENT);
    }

}
