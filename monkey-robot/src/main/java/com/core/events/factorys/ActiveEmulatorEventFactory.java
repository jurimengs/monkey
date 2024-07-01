package com.core.events.factorys;

import com.constant.Constant;
import com.common.abstracts.CustomEventFactory;
import com.core.events.builder.Config;
import com.core.events.gen.ActiveEmulatorEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActiveEmulatorEventFactory implements CustomEventFactory<Config> {
    @Override
    public ActiveEmulatorEvent apply() {
        return new ActiveEmulatorEvent(Constant.Event.ACTIVE_WINDOW_DELAY);
    }

    @Override
    public ActiveEmulatorEvent apply(Config config) {
        return new ActiveEmulatorEvent(config.getDelay());
    }

    public String getName() {
        return this.getClass().getName();
    }
}
