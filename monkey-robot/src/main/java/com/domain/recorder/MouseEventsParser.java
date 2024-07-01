package com.domain.recorder;

import com.alibaba.fastjson.JSON;
import com.core.events.builder.Config;
import com.core.events.builder.MouseClickConfig;
import com.core.events.factorys.*;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.mouse.NativeMouseEvent;
import com.common.abstracts.CustomEvent;
import com.common.abstracts.CustomEventFactory;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MouseEventsParser {
    private GenMouseLeftDoubleClickEventFactory leftDoubleClickEventFactory;
    private GenMouseLeftClickEventFactory genMouseLeftClickEventFactory;
    public MouseEventsParser() {
        log.info("eventMap loaded: {}", JSON.toJSONString(eventMap));
    }

    public CustomEvent parse(NativeMouseEvent mouseEvent) {
        CustomEvent res = null;
        int button = mouseEvent.getButton(); // 左、右、中
        int clickCount = mouseEvent.getClickCount();
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();

        CustomEventFactory customEventFactory = routeFactory(clickCount, button);

        MouseClickConfig config = new MouseClickConfig(x, y);
        res = customEventFactory.apply(config);
        return res;
    }

    private CustomEventFactory routeFactory(int clickCount, int button) {
        if(clickCount >= 2) {
            // 双击
            if(button == MouseEvent.BUTTON1) {
                return new GenMouseLeftDoubleClickEventFactory();
            } else if(button == MouseEvent.BUTTON2) {
                return new GenMouseRightDoubleClickEventFactory();
            } else if(button == MouseEvent.BUTTON3) {
                return new GenMouseMiddleDoubleClickEventFactory();
            }
        } else{
            // 单击
            if(button == MouseEvent.BUTTON1) {
                return new GenMouseLeftClickEventFactory();
            } else if(button == MouseEvent.BUTTON2) {
                return new GenMouseMiddleClickEventFactory();
            } else if(button == MouseEvent.BUTTON3) {
                return new GenMouseRightClickEventFactory();
            }
        }
        log.error("鼠标除了这几个事件，还能有其他事件?");
        return null;
    }

    private static Map<Integer, CustomEventFactory<? extends Config>> eventMap = new HashMap<>();
}
