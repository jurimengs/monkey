package com.domain.recorder;

import com.GlobalManager;
import com.common.abstracts.CustomEvent;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.Iterator;
import java.util.LinkedList;

@Slf4j
public abstract class EventParser {
    private LinkedList<Integer> holdingKeys = new LinkedList<>();

    public CustomEvent parseReleased(NativeKeyEvent event) {
        if(GlobalManager.getInstance().isDebug()) {
            log.info("holdingKeys {}, event.getRawCode: {}", holdingKeys, event.getRawCode());
        }
        CustomEvent res = null;

        try {
            int size = holdingKeys.size();
            if(size > 1) {
                // 组合键
                res = childParse(holdingKeys);
            } else if(size == 1){
                // 单键
                // TODO
            } else {
                // 单键
                log.info("无。。。");
                // TODO
            }
        } catch (Exception e) {
            log.error("some error ", e);
        } finally {
            // 最后都要把自己移除掉
            safeRemove(event.getRawCode());

        }

        return  res;
    }

    private void safeRemove(int rawCodeToRemove) {
        for(Iterator<Integer> iterator = holdingKeys.iterator(); iterator.hasNext();) {
            Integer ele = iterator.next();
            if(ele.intValue() == rawCodeToRemove) {
                holdingKeys.remove(ele);
            }
        }

        if(GlobalManager.getInstance().isDebug()) {
            log.info("holdingKeys after remove {}", holdingKeys);
        }
    }

    /**
     * 先假设 press 不需要返回，但是会记忆操作，返回在释放的时候才捕捉返回
     * @param event
     */
    public void parsePress(NativeKeyEvent event) {
        if(!holdingKeys.contains(event.getRawCode())) {
            synchronized (holdingKeys) {
                holdingKeys.add(event.getRawCode());
            }

        }
    }

    protected abstract CustomEvent childParse(LinkedList<Integer> holdingKeys);

}
