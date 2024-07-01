package com.core.events.factorys;

import com.common.abstracts.CustomEventFactory;
import com.core.events.gen.CtrlCEvent;
import com.core.events.builder.Config;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CtrlCEventFactory implements CustomEventFactory<Config> {
    public CtrlCEvent apply() {
        log.info("ctrl c ： do nothing...");
//        return new CtrlCEvent("", Constant.Event.DELAY);
        return null;
    }

    @Override
    public CtrlCEvent apply(Config config) {
        return null;
    }


    public String getName() {
        return "CtrlCEventFactory";
    }
}
