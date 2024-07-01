package com.domain.recorder;

import com.common.abstracts.CustomEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 */
@Slf4j
public class EventContainer {
    private LinkedBlockingQueue<CustomEvent> eventQueue = new LinkedBlockingQueue<>(5000);

    public void gather(CustomEvent event) {
        log.debug("EventContainer gather event ...");
        boolean fitable = filter(event);
        if(fitable ) {
            eventQueue.add(event);
        }
    }

    public CustomEvent takeEvent() throws InterruptedException {
        return eventQueue.take();
    }

    public int size() {
        return eventQueue.size();
    }

    private boolean filter(CustomEvent event) {
        log.debug("do some filter....");
        return true;
    }

}
