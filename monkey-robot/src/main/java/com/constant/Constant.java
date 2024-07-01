package com.constant;

import java.awt.event.KeyEvent;

public class Constant {
    public static final int EVENT_DELAY_AFTER_EVENT = 500;
    public static final int SCREEN_CAPTURE_EVENT_RETRY_TIMES = 30;
    /**
     * 功能键需要做 execlude 处理
     */
    public static final int[] FunctionKey = {
            KeyEvent.VK_ESCAPE,
            KeyEvent.VK_F1,
            KeyEvent.VK_F2,
            KeyEvent.VK_F3,
            KeyEvent.VK_F4,
            KeyEvent.VK_F5,
            KeyEvent.VK_F6,
            KeyEvent.VK_F7,
            KeyEvent.VK_F8,
            KeyEvent.VK_F9,
            KeyEvent.VK_F10,
            KeyEvent.VK_F11,
            KeyEvent.VK_F12,
    };

    public interface KeyRawCode {
        public static final int CTRL = 162;
    }

    public interface Event {
        public static final int DELAY = 500;
        public static final int MOUSE_CLICK_DELAY = 500;
        public static final int MOUSE_MOVE_DELAY = 1000;
        public static final int ACTIVE_WINDOW_DELAY = 1000;
        /**
         * 每次尝试图片识别间隔时间
         */
        public static final int SCREEN_CAPTURE_MATCH = 2000;
    }

}
