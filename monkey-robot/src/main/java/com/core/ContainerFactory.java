package com.core;

import com.domain.recorder.EventContainer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContainerFactory {
    public static EventContainer createEventContainer() {
        EventContainer container = new EventContainer();
        return container;
    }
}
