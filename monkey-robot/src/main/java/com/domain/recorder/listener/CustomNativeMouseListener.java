package com.domain.recorder.listener;

import com.domain.recorder.MouseEventsParser;
import com.domain.recorder.Recorder;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;
import com.common.abstracts.CustomEvent;

@Slf4j
public class CustomNativeMouseListener implements NativeMouseListener {
    private Recorder recorder;
    private MouseEventsParser mouseEventsParser;

    public CustomNativeMouseListener(Recorder recorder, MouseEventsParser mouseEventsParser) {
        this.recorder = recorder;
        this.mouseEventsParser = mouseEventsParser;
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
//        log.info("mouseClicked : {}", nativeMouseEvent.paramString());
        CustomEvent customEvent = mouseEventsParser.parse(nativeMouseEvent);
        recorder.saveKeyEvent(customEvent);
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
//        log.info("mousePressed : {}", nativeMouseEvent.paramString());
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {
//        log.info("mouseReleased : {}", nativeMouseEvent.paramString());
    }
}
