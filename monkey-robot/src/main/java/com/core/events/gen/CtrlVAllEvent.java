package com.core.events.gen;

import com.constant.Constant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import com.common.abstracts.CustomEvent;
import com.common.utils.SystemUtil;

import java.awt.*;
import java.awt.event.KeyEvent;


// 如果是 CtrlCEvent 事件，按操作逻辑来讲应该是要被丢弃掉， 如果是 CtrlVEnent ，则应该拿粘贴版的内容
// 
@Slf4j
@Data
public class CtrlVAllEvent implements CustomEvent {
    private GenMouseMoveEvent mouseMoveEvent;
    //
    private Object text;
    //
    private int delay;

    public CtrlVAllEvent(String text, int delay) {
        this.text = text;
        this.delay = delay;
    }

    public CtrlVAllEvent(int delay) {
        this.delay = delay;
    }

    @Override
    public void execute(Robot robot) throws InterruptedException {
        if(mouseMoveEvent != null) {
            mouseMoveEvent.execute(robot);
        }

//        ContentUtil.setSysClipboardText(text);

        int vkControl;
        if(SystemUtil.isMacOS()) {
            vkControl = KeyEvent.VK_META;
        } else {
            vkControl = KeyEvent.VK_CONTROL;
        }
        robot.keyPress(vkControl);
        robot.keyPress(KeyEvent.VK_V);
        robot.delay(Constant.EVENT_DELAY_AFTER_EVENT);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(vkControl);
        robot.delay(Constant.EVENT_DELAY_AFTER_EVENT);
        robot.delay(delay);
    }

    public void setMouseMoveEvent(GenMouseMoveEvent mouseMoveEvent) {
        this.mouseMoveEvent = mouseMoveEvent;
    }

    @Override
    public String toString() {
        return "CtrlVEvent{" +
                "text='" + text + '\'' +
                ", delay=" + delay +
                '}';
    }
}
