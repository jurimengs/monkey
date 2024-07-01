package com.domain.recorder.listener;

import com.GlobalManager;
import com.common.abstracts.CustomEvent;
import com.core.utils.FunctionUtil;
import com.domain.recorder.EventParser;
import com.domain.recorder.Recorder;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

@Slf4j
public class CustomNativeKeyListener implements NativeKeyListener {
    private EventParser eventParser;
    private Recorder recorder;


    public CustomNativeKeyListener(Recorder recorder, EventParser eventParser) {
        this.recorder = recorder;
        this.eventParser = eventParser;
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeEvent) {

        if(GlobalManager.getInstance().isDebug()) {
            log.info("type : {}", nativeEvent.paramString());
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {

        if(GlobalManager.getInstance().isDebug()) {
            log.info("pressed : {}", nativeEvent.paramString());
        }

        if(FunctionUtil.isNotFunctionKey(nativeEvent.getRawCode())) {
            eventParser.parsePress(nativeEvent);
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
//        log.info("nativeKeyReleased {}", nativeEvent.getRawCode());s

        if(FunctionUtil.isFunctionKey(nativeEvent.getRawCode())) {
            if(FunctionUtil.isRecordKey(nativeEvent.getRawCode())) {
                GlobalManager.getInstance().startRecord();
            }else if(FunctionUtil.isReplayKey(nativeEvent.getRawCode())) {
                GlobalManager.getInstance().doReplay();
            }

            return;
        }

        CustomEvent customEvent = eventParser.parseReleased(nativeEvent);
        if(customEvent != null) {
            recorder.saveKeyEvent(customEvent);
        } else {
            if(GlobalManager.getInstance().isDebug()) {
                log.error("none event : {}", nativeEvent.paramString());
            }
        }
    }
}
