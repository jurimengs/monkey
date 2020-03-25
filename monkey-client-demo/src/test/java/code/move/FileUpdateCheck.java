package code.move;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class FileUpdateCheck {

    private static final String[] sourceFolders = CodeMove.sourceFolders;
    private static FileWriter cacheWriter;
    private static Map<String, Long> fileTimeCache = new HashMap<>();
    private static Map<String, Boolean> hasKeyCache = new HashMap<>();
    
    /**
     * 第一次使用， 要先run main， 生成一个初始的修改时间记录
     */
    public static void main(String[] args) throws IOException {
        File file = new File(CodeMove.cacheFile);
        if(!file.exists()) {
            file.mkdirs();
        }
        initCacheFile(file);
    }

    private static void initCacheFile(File file) throws IOException {
        cacheWriter = new FileWriter(file);
        
        for (int i = 0; i < sourceFolders.length; i++) {
            String sourceFolder = sourceFolders[i];
            dealSingle(sourceFolder, sourceFolder);
        }
        
        moveSqlMap(CodeMove.sqlSourceFolder);
        
        cacheWriter.close();
    }
    
    public static void init() throws IOException {
        File file = new File(CodeMove.cacheFile);
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            String text = null;
            while((text = bufferedReader.readLine()) != null){
                if(StringUtils.isNotBlank(text)) {
                    String[] arr = text.split("=");
                    String key = arr[0];
                    Long lastModified = Long.valueOf(arr[1]);
                    fileTimeCache.put(key, lastModified);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileInputStream != null) {
                fileInputStream.close();
            }
            if(inputStreamReader != null) {
                inputStreamReader.close();
            }
            if(bufferedReader != null) {
                bufferedReader.close();
            }
        }
    }
    
    public static void reWriteToFile() throws IOException {
        File file = new File(CodeMove.cacheFile);
        cacheWriter = new FileWriter(file);
        
        Set<Entry<String, Long>> entrySet = fileTimeCache.entrySet();
        
        
        for (Iterator<Entry<String, Long>> iterator = entrySet.iterator(); iterator.hasNext();) {
            Entry<String, Long> entry = iterator.next();
            String key = entry.getKey();
            Long value = entry.getValue();
            String content = key + "=" + value;
            cacheWriter.write(content + "\n");
            cacheWriter.flush();
        }
    }
    
    public static boolean isModified(File srcFile) {
        String key = MD5Utils.md5Encrypt(srcFile.getAbsolutePath()); // 文件名 MD5 做 key
        long newModifed = srcFile.lastModified();
        long lastModified = fileTimeCache.get(key);
        
        boolean res = newModifed != lastModified;
        if(res) {
            // 判断过的全都写到 map 里，等待重新写缓存文件
            fileTimeCache.put(key, newModifed);
        }
        
        return res;
    }

    private static void dealSingle(String sourceFolder, String sourceFile) {
        File file = new File(sourceFile);
        if(file.exists()) {
            if(file.isFile()) {
                recordFileUpdateTime(file);
            } else if(file.isDirectory()) {
                String[] list = file.list();
                for (int i = 0; i < list.length; i++) {
                    dealSingle(sourceFolder, sourceFile + "/" + list[i]);
                }
            }
        }
    }

    private static void moveSqlMap(String sqlSourceFolder) throws IOException {
        File file = new File(sqlSourceFolder);
        if(file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if(listFiles != null) {
                for (int i = 0; i < listFiles.length; i++) {
                    recordFileUpdateTime(listFiles[i]);
                }
            }
        }
    }
    
    private static void recordFileUpdateTime(File srcFile) {
        String key = MD5Utils.md5Encrypt(srcFile.getAbsolutePath()); // 文件名 MD5 做 key
        if(!hasKeyCache.containsKey(key)) {
            hasKeyCache.put(key, true);
            long lastModified = srcFile.lastModified();
            String content = key + "=" + lastModified;
            
            try {
                cacheWriter.write(content + "\n");
                cacheWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
      
        
    }

}
