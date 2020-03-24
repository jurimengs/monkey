package code.move;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;

public class FileUpdateCheck {

    private static String relativePath = "src/main/java";
    private static String aimPath = "E:/codes/xlc-cloud/xlc-cloud-main/" + relativePath;
    private static FileWriter fileWriter;

    private static Map<String, Long> fileTimeCache = new HashMap<>();
    
    private static Map<String, Boolean> hasKeyCache = new HashMap<>();
    
    /**
     * 第一次使用， 要先run main， 生成一个初始的修改时间记录
     */
    public static void main(String[] args) throws IOException {
        File file = new File("E:/codes/cache/cache.txt");
        if(!file.exists()) {
            file.mkdirs();
        }
        fileWriter = new FileWriter(file);
        
        String[] sourceFolders = {
                "E:/codes/xinlecai/xinlecai_server_springmvc/service/service-api/",
                "E:/codes/xinlecai/xinlecai_server_springmvc/service/service-impl/",
                "E:/codes/xinlecai/xinlecai_server_springmvc/service/service-impl-rmq/",
                "E:/codes/xinlecai/xinlecai_server_springmvc/service/xlc-ops-b/"
        };
        fileWriter.write("");
        fileWriter.flush();
        
        for (int i = 0; i < sourceFolders.length; i++) {
            String sourceFolder = sourceFolders[i] + relativePath;
            dealSingle(sourceFolder, sourceFolder);
        }
        fileWriter.close();

    }
    
    public static void init() throws IOException {
        File file = new File("E:/codes/cache/cache.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
         
        String text = null;
        while((text = bufferedReader.readLine()) != null){
            if(StringUtils.isNotBlank(text)) {
                String[] arr = text.split("=");
                String key = arr[0];
                Long lastModified = Long.valueOf(arr[1]);
                fileTimeCache.put(key, lastModified);
            }
        }
    }
    
    public static boolean isModified(File srcFile) {
        String key = MD5Encoder.encode(srcFile.getAbsolutePath().getBytes()); // 文件名 MD5 做 key
        long newModifed = srcFile.lastModified();
        
        long lastModified = fileTimeCache.get(key);
        return newModifed != lastModified;
    }

    private static void dealSingle(String sourceFolder, String sourceFile) {
        File file = new File(sourceFile);
        if(file.exists()) {
            if(file.isFile()) {
                
                String sourcePath = file.getAbsolutePath();
                String fileRelativePath = sourcePath.substring(sourceFolder.length());
                recordFileUpdateTime(file, aimPath + fileRelativePath);
            } else if(file.isDirectory()) {
                String[] list = file.list();
                for (int i = 0; i < list.length; i++) {
                    dealSingle(sourceFolder, sourceFile + "/" + list[i]);
                }
            }
        }
    }

    private static void recordFileUpdateTime(File srcFile, String fileWriteToPath) {
        String key = MD5Encoder.encode(srcFile.getAbsolutePath().getBytes()); // 文件名 MD5 做 key
        if(!hasKeyCache.containsKey(key)) {
            hasKeyCache.put(key, true);
            long lastModified = srcFile.lastModified();
            String content = key + "=" + lastModified;
            
            try {
                fileWriter.write((content + "\n"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
      
        
    }

}
