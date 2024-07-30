package window;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

public class User32TestActiveCertainWindow {

    public static void main(String[] args) {
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, "浏览器 - MuMu模拟器");
        if (hwnd == null) {
            System.out.println("QQ is not running");
        } else{
            User32.INSTANCE.ShowWindow(hwnd, 9 );        // SW_RESTORE
            User32.INSTANCE.SetForegroundWindow(hwnd);   // bring to front

            //User32.INSTANCE.GetForegroundWindow() //获取现在前台窗口
            WinDef.RECT qqwin_rect = new  WinDef.RECT();
            User32.INSTANCE.GetWindowRect(hwnd, qqwin_rect);
            int qqwin_width = qqwin_rect.right-qqwin_rect.left;
            int qqwin_height = qqwin_rect.bottom-qqwin_rect.top;

            User32.INSTANCE.MoveWindow(hwnd, 700, 100, qqwin_width, qqwin_height, true);
            for(int i = 700; i > 100; i -=10) {
                User32.INSTANCE.MoveWindow(hwnd, i, 100, qqwin_width, qqwin_height, true);   // bring to front
                try {
                    Thread.sleep(80);
                }catch(Exception e){}
            }
            //User32.INSTANCE.PostMessage(hwnd, WinUser.WM_CLOSE, null, null);  // can be WM_QUIT in some occasio
        }

    }
}
