package com.core.events.factorys;

import com.constant.Constant;
import com.core.events.builder.MouseClickConfig;
import com.core.events.gen.GenMouseLeftDoubleClickEvent;
import lombok.extern.slf4j.Slf4j;
import com.common.abstracts.CustomEventFactory;

@Slf4j
public class GenMouseLeftDoubleClickEventFactory implements CustomEventFactory<MouseClickConfig> {
    @Override
    public GenMouseLeftDoubleClickEvent apply() {
        return new GenMouseLeftDoubleClickEvent(Constant.Event.MOUSE_CLICK_DELAY);
    }

    @Override
    public GenMouseLeftDoubleClickEvent apply(MouseClickConfig config) {
        return new GenMouseLeftDoubleClickEvent(config.getDelay(), config.getMouseMoveEvent());
    }

    public String getName() {
        return this.getClass().getName();
    }
}
