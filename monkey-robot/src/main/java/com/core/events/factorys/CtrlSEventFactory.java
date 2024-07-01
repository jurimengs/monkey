package com.core.events.factorys;

import com.constant.Constant;
import com.core.events.builder.Config;
import com.core.utils.ContentUtil;
import com.common.abstracts.CustomEvent;
import com.common.abstracts.CustomEventFactory;
import com.core.events.gen.CtrlVEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CtrlSEventFactory implements CustomEventFactory<Config> {
    @Override
    public CustomEvent apply() {
        String text = ContentUtil.getSysClipboardText();
        log.info("ctrl s : [{}]");
        return new CtrlVEvent(text, Constant.Event.DELAY);
    }

    @Override
    public CustomEvent apply(Config config) {
        String text = ContentUtil.getSysClipboardText();
        log.info("ctrl s : [{}]");
        return new CtrlVEvent(text, config.getDelay());
    }

    public String getName() {
        return "CtrlSEventFactory";
    }
}
