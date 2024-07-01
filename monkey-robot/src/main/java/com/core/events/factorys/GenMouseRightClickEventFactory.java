package com.core.events.factorys;

import com.constant.Constant;
import com.core.events.builder.MouseClickConfig;
import com.core.events.gen.GenMouseRightClickEvent;
import lombok.extern.slf4j.Slf4j;
import com.common.abstracts.CustomEventFactory;

@Slf4j
public class GenMouseRightClickEventFactory implements CustomEventFactory<MouseClickConfig> {
    @Override
    public GenMouseRightClickEvent apply() {
        return new GenMouseRightClickEvent(Constant.Event.MOUSE_CLICK_DELAY);
    }

    @Override
    public GenMouseRightClickEvent apply(MouseClickConfig config) {
        return new GenMouseRightClickEvent(config.getDelay(), config.getMouseMoveEvent());
    }

    public String getName() {
        return this.getClass().getName();
    }

}
