package com.core.events.factorys;

import com.constant.Constant;
import com.common.abstracts.CustomEventFactory;
import com.core.events.builder.ScreenCaptureConfig;
import com.core.events.gen.ScreenCaptureEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScreenCaptureEventFactory implements CustomEventFactory<ScreenCaptureConfig> {
    @Override
    public ScreenCaptureEvent apply() {
        return new ScreenCaptureEvent(Constant.Event.ACTIVE_WINDOW_DELAY);
    }

    @Override
    public ScreenCaptureEvent apply(ScreenCaptureConfig config) {
        return new ScreenCaptureEvent(
                config.getLeftx(), config.getLefty(),
                config.getWidth(), config.getHeight(),
                config.getDelay(), config.getTempName());
    }

    public String getName() {
        return this.getClass().getName();
    }
}
