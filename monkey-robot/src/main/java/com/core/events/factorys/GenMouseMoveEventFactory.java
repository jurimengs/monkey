package com.core.events.factorys;

import com.constant.Constant;
import com.common.abstracts.CustomEvent;
import com.common.abstracts.CustomEventFactory;
import com.core.events.builder.Config;
import com.core.events.gen.GenMouseMoveEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenMouseMoveEventFactory implements CustomEventFactory<Config> {
    @Override
    public GenMouseMoveEvent apply() {
        return new GenMouseMoveEvent(Constant.Event.MOUSE_MOVE_DELAY);
    }

    @Override
    public CustomEvent apply(Config config) {
        return new GenMouseMoveEvent(config.getDelay());
    }

    public String getName() {
        return this.getClass().getName();
    }
}
