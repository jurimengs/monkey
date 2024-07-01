package com.domain.replayer;

import com.common.abstracts.CustomEvent;
import com.core.exception.MessageAlertException;
import com.domain.recorder.EventContainer;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

@Slf4j
public class MemoryReplayer extends Replayer {
    private ExecutorService executorService = Executors.newSingleThreadExecutor(new ReplayThreadFactory("MemoryReplayer"));

    public MemoryReplayer(EventContainer container) {
        super(container);
    }

    public void init() throws InterruptedException {

    }

    public void doReplay(Function function) {
        log.info("doReplay ...{}");
        executorService.execute(() -> {
            startReplay();
            try {
                EventContainer container = getEventContainer();
                Robot robot = getRobot();
                while(true) {
                    log.debug("take event task to do : [{}]",  container.size());
                    CustomEvent customEvent = container.takeEvent();
                    customEvent.execute(robot);
                    log.debug("execute event :  [{}]", customEvent);
                }
            } catch (MessageAlertException e) {
                log.error("业务中断：{}", e.getMessage());
            } catch (InterruptedException e) {
                log.error("流程异常", e);
            }
            stopReplay(function);
        });
    }

    public void shutDown() {
        if(!executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

}
