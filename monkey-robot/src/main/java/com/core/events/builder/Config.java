package com.core.events.builder;

public abstract class Config {
    //
    private int delay;

    public Config(int delay) {
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }


}
