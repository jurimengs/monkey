package com.core.widow;

import com.sun.jna.Native;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinUser;

// 提供 获取窗口句柄  获取窗口矩形坐标 鼠标控制
public interface User32 extends StdCallLibrary {
    User32 INSTANCE = (User32) Native.load("user32", User32.class);
    boolean EnumWindows(WinUser.WNDENUMPROC lpEnumFunc, Pointer arg);

    /**
     *
     * @param hWnd
     * @param lpString 名称会由 native api 写入进来
     * @param nMaxCount
     * @return
     */
    int GetWindowTextA(HWND hWnd, byte[] lpString, int nMaxCount);



    public int GetWindowThreadProcessId(HWND hWnd, PointerByReference pref);

    public HWND GetForegroundWindow();

    public int GetWindowTextW(HWND hWnd, char[] lpString, int nMaxCount);

}
