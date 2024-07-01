package com.core.events.factorys;

import com.constant.Constant;
import com.core.events.builder.Config;
import com.core.events.gen.CtrlVEvent;
import com.core.utils.ContentUtil;
import com.common.abstracts.CustomEvent;
import com.common.abstracts.CustomEventFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CtrlVEventFactory implements CustomEventFactory<Config> {
    @Override
    public CustomEvent apply() {
        String text = ContentUtil.getSysClipboardText();
        return new CtrlVEvent(text, Constant.Event.DELAY);
    }

    @Override
    public CustomEvent apply(Config config) {
        String text = ContentUtil.getSysClipboardText();
        return new CtrlVEvent(text, config.getDelay());
    }

    public String getName() {
        return "CtrlVEventFactory";
    }
}
