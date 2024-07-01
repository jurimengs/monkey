package com.core.events.gen;

import com.constant.Constant;
import lombok.Data;
import com.common.abstracts.CustomEvent;

import java.awt.*;
import java.awt.event.KeyEvent;

@Data
public class CtrlSEvent implements CustomEvent {
    //
    private String text;
    //
    private int delay;

    public CtrlSEvent(String text, int delay) {
        this.text = text;
        this.delay = delay;
    }
    public CtrlSEvent(int delay) {
        this.delay = delay;
    }

    @Override
    public void execute(Robot robot) throws InterruptedException {
        robot.delay(delay);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_S);
        robot.delay(Constant.EVENT_DELAY_AFTER_EVENT);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_S);
        robot.delay(Constant.EVENT_DELAY_AFTER_EVENT);
    }

    @Override
    public String toString() {
        return "CtrlCEvent{" +
                "text='" + text + '\'' +
                ", delay=" + delay +
                '}';
    }
}
