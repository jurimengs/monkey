package com.core.events.gen;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.constant.Constant;
import lombok.Data;
import com.common.abstracts.CustomEvent;
import com.core.widow.User32;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;

@Slf4j
@Data
public class ActiveEmulatorEvent implements CustomEvent {
    private int delay;

    public ActiveEmulatorEvent(int delay) {
        this.delay = delay;
    }

    @Override
    public void execute(Robot robot) throws InterruptedException{
        robot.delay(delay);

        final User32 user32 = User32.INSTANCE;
        user32.EnumWindows(new WinUser.WNDENUMPROC() {
            /**
             *
             * @param hwnd window handle
             * @param arg1
             * @return return true will iterator to next , return false will stop iteratoring
             */
            @Override
            public boolean callback(WinDef.HWND hwnd, Pointer arg1) {
                byte[] windowText = new byte[1024];
                user32.GetWindowTextA(hwnd, windowText, 512);
                Pointer pointer = hwnd.getPointer();

                String wText = Native.toString(windowText, "GBK");
                // get rid of this if block if you want all windows regardless of whether
                // or not they have text
                if (wText.isEmpty()) {
                    return true;
                }


                if(StringUtils.contains(wText, "MuMu模拟器")) {
                    System.out.println("active window MuMu模拟器");
                    com.sun.jna.platform.win32.User32.INSTANCE.ShowWindow(hwnd, 9 );        // SW_RESTORE
                    robot.delay(delay);
                    com.sun.jna.platform.win32.User32.INSTANCE.SetForegroundWindow(hwnd);   // bring to front
                    log.info("active window : {}");
                    return false;
                }
                return true;
            }

        }, null);

        robot.delay(Constant.EVENT_DELAY_AFTER_EVENT);
    }

}
