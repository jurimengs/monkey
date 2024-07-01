package com.core.events.builder;

import com.constant.Constant;

public class MouseMoveConfig extends Config{
    private int aimx;
    private int aimy;
    //
    public MouseMoveConfig(int x, int y) {
        super(Constant.Event.MOUSE_MOVE_DELAY);
        this.aimx = x;
        this.aimy = y;
    }

}
