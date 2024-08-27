package com.monkey.spider;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * 参考 org.apache.logging.log4j.util.PropertyFilePropertySource.loadPropertiesFile
 * version=2.12.1
 * groupId=org.apache.logging.log4j
 * artifactId=log4j-api
 */
@Slf4j
public class FileUtil {

    public static void writeFile(byte[] after, File aimFile) throws FileNotFoundException {
        InputStream in = new ByteArrayInputStream(after);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(aimFile);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("写文件异常 " + aimFile.getName());
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    System.out.println("关闭 fos 异常");
                }
            }
        }

    }
}
