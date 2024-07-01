package com.domain.recorder;

import com.alibaba.fastjson.JSON;
import com.constant.Constant;
import com.common.abstracts.CustomEvent;
import com.common.abstracts.CustomEventFactory;
import com.core.events.builder.Config;
import com.core.events.factorys.CtrlCEventFactory;
import com.core.events.factorys.CtrlSEventFactory;
import com.core.events.factorys.CtrlVEventFactory;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class GroupKeyCodeEventsParser extends EventParser{
    public GroupKeyCodeEventsParser() {
        eventMap.put((Constant.KeyRawCode.CTRL + KeyEvent.VK_C), new CtrlCEventFactory());
        eventMap.put((Constant.KeyRawCode.CTRL + KeyEvent.VK_V), new CtrlVEventFactory());
        eventMap.put((Constant.KeyRawCode.CTRL + KeyEvent.VK_S), new CtrlSEventFactory());
        log.info("eventMap loaded: {}", JSON.toJSONString(eventMap));
    }

    @Override
    protected CustomEvent childParse(LinkedList<Integer> holdingKeys) {
        CustomEvent res = null;
        Integer collect = holdingKeys.stream().collect(Collectors.summingInt(keyRawCode -> keyRawCode));
        if(eventMap.containsKey(collect)) {
            CustomEventFactory customEventFactory = eventMap.get(collect);
            res = customEventFactory.apply();
        } else {
            log.error("未知的组合键 ： {}", holdingKeys);
        }
        return res;
    }

    private static Map<Integer, CustomEventFactory<? extends Config>> eventMap = new HashMap<>();
}
