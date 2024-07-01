package com.core.widow;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class Psapi {

    static {
        Native.register("psapi");
    }

    public static native int GetModuleBaseNameW(Pointer hProcess, Pointer hmodule, char[] lpBaseName, int size);

}
