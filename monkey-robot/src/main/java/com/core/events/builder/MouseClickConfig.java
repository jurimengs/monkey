package com.core.events.builder;

import com.constant.Constant;
import com.core.events.gen.GenMouseMoveEvent;

public class MouseClickConfig extends Config{
    private GenMouseMoveEvent mouseMoveEvent;
    //
    public MouseClickConfig(int x, int y) {
        super(Constant.Event.MOUSE_CLICK_DELAY);
        this.mouseMoveEvent = new GenMouseMoveEvent(x, y, Constant.Event.MOUSE_MOVE_DELAY);
    }

    public GenMouseMoveEvent getMouseMoveEvent() {
        return mouseMoveEvent;
    }
}
