package com.core.events.factorys;

import com.constant.Constant;
import com.common.abstracts.CustomEventFactory;
import com.core.events.builder.MouseClickConfig;
import com.core.events.gen.GenMouseLeftClickEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenMouseLeftClickEventFactory implements CustomEventFactory<MouseClickConfig> {
    @Override
    public GenMouseLeftClickEvent apply() {
        return new GenMouseLeftClickEvent(Constant.Event.MOUSE_CLICK_DELAY);
    }

    @Override
    public GenMouseLeftClickEvent apply(MouseClickConfig config) {
        return new GenMouseLeftClickEvent(config.getDelay(), config.getMouseMoveEvent());
    }

    public String getName() {
        return this.getClass().getName();
    }

}
