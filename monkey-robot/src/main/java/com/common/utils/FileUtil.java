package com.common.utils;

import com.common.constant.CommonConstant;
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
    public static Collection<URL> findResources(final String resource) {
        final Collection<UrlResource> urlResources = findUrlResources(resource);
        final Collection<URL> resources = new LinkedHashSet<>(urlResources.size());
        for (final UrlResource urlResource : urlResources) {
            resources.add(urlResource.getUrl());
        }
        return resources;
    }

    public static Collection<UrlResource> findUrlResources(final String resource) {
        // @formatter:off
        final ClassLoader[] candidates = {
                FileUtil.class.getClassLoader(),
                ClassLoader.getSystemClassLoader()};
        // @formatter:on
        final Collection<UrlResource> resources = new LinkedHashSet<>();
        for (final ClassLoader cl : candidates) {
            if (cl != null) {
                try {
                    final Enumeration<URL> resourceEnum = cl.getResources(resource);
                    while (resourceEnum.hasMoreElements()) {
                        resources.add(new UrlResource(cl, resourceEnum.nextElement()));
                    }
                } catch (final IOException e) {
                    log.error("搜寻配置文件路径异常", e);
                }
            }
        }
        return resources;
    }

    public static String readContent(String filePath) {
        if(StringUtils.isEmpty(filePath)) {
            log.error("none flow...{}" , filePath);
            return StringUtils.EMPTY;
        }
        File file = new File(filePath);
        return readContent(file);
    }

    public static String readContent(File file) {
        BufferedReader reader = null;
        FileReader fileReader = null;
        StringBuffer sb = new StringBuffer();
        try {
            fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sb.append(tempStr);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    log.error("close BufferedReader failed", e1);
                }
            }
            if(fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    log.error("close fileReader failed", 3);
                }
            }
        }
        return sb.toString();
    }


    public static byte[] readBytes(File file) {
        byte[] fileBytes = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            fileBytes = new byte[(int) file.length()];
            fis.read(fileBytes);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("不存在的文件...");
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("关闭 fis 异常");
                }
            }
        }
        return fileBytes;

    }

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

    public static void deletePath(String path) {
        File file = new File(path);
        deleteFile(file);
    }

    public static void deleteFile(File file) {
        if(file != null) {
            if(file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
        }
        file.delete();
        System.out.println("删除文件及子文件 " + file.getName());
    }

    public static void deleteFiles(List<File> splitFile) {
        Iterator<File> iterator = splitFile.iterator();
        while (iterator.hasNext()) {
            File nextElement =  iterator.next();
            nextElement.delete();
        }
    }

    /**
     * 将一组数据固定分组，每组n个元素
     *
     * @param <T> 泛型对象
     * @param resList 需要拆分的集合
     * @param subListLength 每个子集合的元素个数
     * @return 返回拆分后的各个集合组成的列表
     * 代码里面用到了guava和common的结合工具类
     **/
    public static <T> List<List<T>> split(List<T> resList, int subListLength) {
        if (CollectionUtils.isEmpty(resList) || subListLength <= 0) {
            return new ArrayList<>();
        }
        List<List<T>> ret = new ArrayList<>();
        int size = resList.size();
        if (size <= subListLength) {
            // 数据量不足 subListLength 指定的大小
            ret.add(resList);
        } else {
            int pre = size / subListLength;
            int last = size % subListLength;
            // 前面pre个集合，每个大小都是 subListLength 个元素
            for (int i = 0; i < pre; i++) {
                List<T> itemList = new ArrayList<>();
                for (int j = 0; j < subListLength; j++) {
                    itemList.add(resList.get(i * subListLength + j));
                }
                ret.add(itemList);
            }
            // last的进行处理
            if (last > 0) {
                List<T> itemList = new ArrayList<>();
                for (int i = 0; i < last; i++) {
                    itemList.add(resList.get(pre * subListLength + i));
                }
                ret.add(itemList);
            }
        }
        return ret;
    }

    /**
     * 将一组数据平均分成n组
     *
     * @param source 要分组的数据源
     * @param n      平均分成n组
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % n;  //(先计算出余数)
        int number = source.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

    public static boolean isText(File file) {
        if(file.isDirectory()) {
            return false;
        }
        String fileType = cn.hutool.core.io.FileUtil.getType(file);
        return StringUtils.equalsIgnoreCase(fileType, "txt");
    }

    public static boolean isTsFile(File file) {
        if(file.isDirectory()) {
            return false;
        }
        String fileType = cn.hutool.core.io.FileUtil.getType(file);
        return StringUtils.equalsIgnoreCase(fileType, "ts");
    }

    public static boolean isKeyFile(File file) {
        if(file.isDirectory()) {
            return false;
        }
        String fileType = cn.hutool.core.io.FileUtil.getType(file);
        return StringUtils.equalsIgnoreCase(fileType, "key");
    }

    public static boolean isMovie(File selectedDirectory) {
        if(selectedDirectory == null) {
            return false;
        }
        if(selectedDirectory.isDirectory()) {
            return false;
        }
        try{
            String fileType = cn.hutool.core.io.FileUtil.getType(selectedDirectory);
            return StringUtils.equalsAnyIgnoreCase(fileType, CommonConstant.FileType.MOVIE);
        } catch (Exception e) {
            log.error("读取文件类型失败 {}", e.getMessage());
            return false;
        }

    }

}
