package com;

import com.domain.recorder.Recorder;
import com.domain.replayer.MemoryReplayer;
import com.domain.replayer.Replayer;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

@Slf4j
public class GlobalManager {
    private static GlobalManager INSTANCE = null;
    private ReentrantLock locker = new ReentrantLock();

    private Recorder recorder;
    private Replayer replayer;
    private boolean debug;

    public static GlobalManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new GlobalManager();
        }
        return INSTANCE;
    }

    public void init() throws InterruptedException {
        recorder.init();
        replayer.init();
    }

    public void DEBUG() {
        this.debug = true;
    }

    // 做录制的时候，可以借这个方法做录制
    public void startRecord() {
        log.info("start record ...");
        recorder.startRecord();
    }

    public void doReplay() {
        log.info("doReplay ...");
        try{
            locker.lock();
            log.info("recorder.isStart ...{}", recorder.isStart());
            if(recorder.isStart()) {
                recorder.stopRecord();
            }
            replayer.doReplay(new Function() {
                @Override
                public Object apply(Object o) {
                    log.info("开启录制形态。。。");
                    recorder.startRecord();
                    return null;
                }
            });
        } catch (Exception e) {
            log.error("", e);
        } finally {
            locker.unlock();
        }

    }

    public void unRegistListener() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            log.error("stop listening error...", e);
        }
    }

    private GlobalManager() {
        recorder = new Recorder();
        replayer = new MemoryReplayer(recorder.getContainer());
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean isStartRecord() {
        return recorder.isStart();
    }

    public boolean isStartReplay() {
        return replayer.isStart();
    }
}
