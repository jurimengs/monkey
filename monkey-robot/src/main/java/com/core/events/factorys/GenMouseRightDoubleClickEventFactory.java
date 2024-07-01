package com.core.events.factorys;

import com.constant.Constant;
import com.core.events.builder.MouseClickConfig;
import com.core.events.gen.GenMouseRightDoubleClickEvent;
import lombok.extern.slf4j.Slf4j;
import com.common.abstracts.CustomEventFactory;

@Slf4j
public class GenMouseRightDoubleClickEventFactory implements CustomEventFactory<MouseClickConfig> {
    @Override
    public GenMouseRightDoubleClickEvent apply() {
        return new GenMouseRightDoubleClickEvent(Constant.Event.MOUSE_CLICK_DELAY);
    }

    @Override
    public GenMouseRightDoubleClickEvent apply(MouseClickConfig config) {
        return new GenMouseRightDoubleClickEvent(config.getDelay(), config.getMouseMoveEvent());
    }

    public String getName() {
        return this.getClass().getName();
    }
}
