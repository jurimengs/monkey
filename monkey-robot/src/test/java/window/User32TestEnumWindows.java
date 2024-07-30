package window;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.yunwow.os.robot.core.widow.User32;
import org.apache.commons.lang3.StringUtils;

public class User32TestEnumWindows {
    public static void main(String[] args) {
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
                    com.sun.jna.platform.win32.User32.INSTANCE.SetForegroundWindow(hwnd);   // bring to front
                    return false;
                }
                System.out.println("Found window with text " + hwnd + ",  Text: " + wText);

                return true;
            }

        }, null);

    }
}
