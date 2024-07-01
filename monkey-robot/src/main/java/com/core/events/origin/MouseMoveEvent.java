package com.core.events.origin;

import com.common.abstracts.CustomEvent;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class MouseMoveEvent implements CustomEvent {

    private int originX;
    private int originY;
    private int aimX;
    private int aimY;
    private int eventType; // 0 : click 1: dbclick 2: right click 3: 选中 4:
    private int delay;

    public MouseMoveEvent(int originX, int originY, int x, int y, int delay) {
        this.aimX = x;
        this.aimY = y;
        this.originX = originX;
        this.originY = originY;
        this.delay = delay;
    }

    @Override
    public void execute(Robot robot) throws InterruptedException {
        robot.delay(delay);
        robot.mouseMove(aimX, aimY);
    }
}
