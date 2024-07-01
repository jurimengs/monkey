package com.core.events.gen;

import com.constant.Constant;
import lombok.Data;
import com.common.abstracts.CustomEvent;

import java.awt.*;

@Data
public class GenMouseMoveEvent implements CustomEvent {
    //
    private int x;
    private int y;
    //
    private int delay;

    public GenMouseMoveEvent(int x, int y, int delay) {
        this.x = x;
        this.y = y;
        this.delay = delay;
    }


    public GenMouseMoveEvent(int delay) {
        this.delay = delay;
    }

    @Override
    public void execute(Robot robot) throws InterruptedException{
        robot.delay(delay);
        robot.mouseMove(x, y);
        robot.delay(Constant.EVENT_DELAY_AFTER_EVENT);
    }

    public GenMouseMoveEvent posX(int x) {
        this.x = x;
        return this;
    }

    public GenMouseMoveEvent posY(int y) {
        this.y = y;
        return this;
    }

    @Override
    public String toString() {
        return "MouseMoveEvent{" +
                "x=" + x +
                ", y=" + y +
                ", delay=" + delay +
                '}';
    }
}
