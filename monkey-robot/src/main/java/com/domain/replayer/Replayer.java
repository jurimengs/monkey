package com.domain.replayer;

import com.core.RobotFactory;
import com.domain.recorder.EventContainer;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.function.Function;

@Slf4j
public abstract class Replayer {
    private String name;
    private Robot robot;
    private EventContainer container;
    private boolean start = true;

    public Replayer(EventContainer container) {
        this.robot = RobotFactory.createRobot();
        this.container = container;
    }

    public EventContainer getEventContainer() {
        return container;
    }

    public Robot getRobot() {
        return robot;
    }

    public boolean isStart() {
        return start;
    }

    protected void stopReplay(Function function) {
        log.info("stopReplay ...{}");
        this.start = false;
        function.apply(this);
    }

    protected void startReplay() {
        log.info("startReplay ...{}");
        this.start = true;
    }


    public abstract void init() throws InterruptedException;

    public abstract void doReplay(Function function);

    public abstract void shutDown() ;
}
