package com.core.events.factorys;

import com.constant.Constant;
import com.core.events.builder.MouseClickConfig;
import com.core.events.gen.GenMouseMiddleClickEvent;
import lombok.extern.slf4j.Slf4j;
import com.common.abstracts.CustomEventFactory;

@Slf4j
public class GenMouseMiddleClickEventFactory implements CustomEventFactory<MouseClickConfig> {
    @Override
    public GenMouseMiddleClickEvent apply() {
        return new GenMouseMiddleClickEvent(Constant.Event.MOUSE_CLICK_DELAY);
    }

    @Override
    public GenMouseMiddleClickEvent apply(MouseClickConfig config) {
        return new GenMouseMiddleClickEvent(config.getDelay(), config.getMouseMoveEvent());
    }

    public String getName() {
        return this.getClass().getName();
    }

}
