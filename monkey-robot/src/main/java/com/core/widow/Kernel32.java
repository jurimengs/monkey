package com.core.widow;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class Kernel32 {
    static {
        Native.register("kernel32");
    }

    public static int PROCESS_QUERY_INFORMATION = 0x0400;

    public static int PROCESS_VM_READ = 0x0010;

    public static native int GetLastError();

    public static native Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, Pointer pointer);

}
