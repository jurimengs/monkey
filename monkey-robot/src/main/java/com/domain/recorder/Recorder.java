package com.domain.recorder;

import com.core.ContainerFactory;
import com.common.abstracts.CustomEvent;
import com.domain.recorder.listener.CustomNativeKeyListener;
import com.domain.recorder.listener.CustomNativeMouseListener;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.dispatcher.DefaultDispatchService;

import java.awt.*;

@Slf4j
public class Recorder {
    private Dimension dim; //存储屏幕尺寸
    public double screenWidth;
    public double screenHeight;
    private String name;
    private EventContainer container;
    private boolean start;
    private EventParser eventParser;
    private MouseEventsParser mouseEventsParser;

    public Recorder(){
        this.eventParser = new GroupKeyCodeEventsParser();
        this.mouseEventsParser = new MouseEventsParser();
        this.container = ContainerFactory.createEventContainer();
    }

    public void init() {
        this.dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = dim.getWidth();
        this.screenHeight = dim.getHeight();

        log.debug("屏幕大小为：" + screenWidth + " " + screenHeight);

        try {
            this.registListener();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

    public void registListener() throws NativeHookException {
        CustomNativeKeyListener keyListener = new CustomNativeKeyListener(this, eventParser);
        CustomNativeMouseListener mouseListener = new CustomNativeMouseListener(this, mouseEventsParser);
        //添加监听调度员，如果是Swing程序，就用SwingDispatchService，不是就用默认的
        GlobalScreen.setEventDispatcher(new DefaultDispatchService());
        try {
            //注册监听
            GlobalScreen.registerNativeHook();
            //加入键盘监听，
            GlobalScreen.addNativeKeyListener(keyListener);
            // 加入鼠标监听
            GlobalScreen.addNativeMouseListener(mouseListener);
        } catch (NativeHookException e) {
            log.error("...", e);
        }

    }


    public void saveKeyEvent(CustomEvent event) {
        if(! start) {
            return;
        }
        log.info("save event...{} ", event.toString());
        if(event != null) {
            container.gather(event);
        }
    }


    public EventContainer getContainer() {
        return container;
    }

    public EventContainer getEventContainer() {
        return container;
    }

    public void stopRecord() {
        this.start = false;
    }
    public void startRecord() {
        this.start = true;
    }

    public boolean isStart() {
        return start;
    }
}
