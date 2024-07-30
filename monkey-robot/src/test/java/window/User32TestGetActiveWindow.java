package window;

import com.sun.jna.Native;
import com.sun.jna.ptr.PointerByReference;
import com.yunwow.os.robot.core.widow.User32;

public class User32TestGetActiveWindow {
    private static final int MAX_TITLE_LENGTH = 1024;

    public static void main(String[] args) {
        final User32 user32 = User32.INSTANCE;
        char[] buffer = new char[MAX_TITLE_LENGTH * 2];

        user32.GetWindowTextW(user32.GetForegroundWindow(), buffer, MAX_TITLE_LENGTH);

        System.out.println("Active window title: " + Native.toString(buffer));

        PointerByReference pointer = new PointerByReference();
        int what = user32.GetWindowThreadProcessId(user32.GetForegroundWindow(), pointer);
        System.out.println("what : " + what);

    }
}
