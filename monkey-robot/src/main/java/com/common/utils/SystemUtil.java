package com.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class SystemUtil {
    public static String getUserHome() {
        return System.getProperty("user.home");
    }

    public static boolean isMacOS() {
        String osName = System.getProperty("os.name");
        log.info("osName {}", osName);
        return StringUtils.contains(osName, "Mac");
    }
}
