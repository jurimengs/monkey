package com.core.utils;

import com.constant.Constant;

import java.awt.event.KeyEvent;

public class FunctionUtil {
    public static boolean isFunctionKey(int keyCode) {
        for (int i = 0; i < Constant.FunctionKey.length; i++) {
            if(Constant.FunctionKey[i] == keyCode) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotFunctionKey(int keyCode) {
        return !isFunctionKey(keyCode);
    }

    public static boolean isListenerKey(int keyCode) {
        return keyCode == KeyEvent.VK_ESCAPE;
    }

    public static boolean isRecordKey(int keyCode) {
        return keyCode == KeyEvent.VK_F5;
    }

    public static boolean isReplayKey(int keyCode) {
        return keyCode == KeyEvent.VK_F6;
    }
}
