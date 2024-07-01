package com.core.events.gen;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import com.common.abstracts.CustomEvent;

import java.awt.*;

@Slf4j
@Data
public class NullEvent implements CustomEvent {
    //
    private String text = "null event";

    @Override
    public void execute(Robot robot) throws InterruptedException {
        log.info("null event");
    }

    @Override
    public String toString() {
        return "NullEvent{" +
                "text='" + text + '\'' +
                '}';
    }
}
