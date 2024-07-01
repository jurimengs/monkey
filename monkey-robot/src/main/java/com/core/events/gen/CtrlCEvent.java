package com.core.events.gen;

import com.constant.Constant;
import lombok.Data;
import com.common.abstracts.CustomEvent;

import java.awt.*;
import java.awt.event.KeyEvent;


// 如果是 CtrlCEvent 事件，按操作逻辑来讲应该是要被丢弃掉， 如果是 CtrlVEnent ，则应该拿粘贴版的内容
@Data
public class CtrlCEvent implements CustomEvent {
    //
    private String text;
    //
    private int delay;

    public CtrlCEvent(String text, int delay) {
        this.text = text;
        this.delay = delay;
    }

    public CtrlCEvent(int delay) {
        this.delay = delay;
    }

    @Override
    public void execute(Robot robot) throws InterruptedException {
        robot.delay(delay);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_C);
        robot.delay(Constant.EVENT_DELAY_AFTER_EVENT);
        robot.keyRelease(KeyEvent.VK_C);
        robot.keyRelease(KeyEvent.VK_CONTROL);
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
