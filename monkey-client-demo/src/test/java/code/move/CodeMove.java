package code.move;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class CodeMove {
    public static final String cacheFile = "E:/codes/cache/cache.txt";
    public static final String logFile = "E:/codes/cache/log.txt";

    public static String[] sourceFolders = {
            "E:/codes/xinlecai/xinlecai_server_springmvc/service/service-api/src/main/java",
            "E:/codes/xinlecai/xinlecai_server_springmvc/service/service-impl/src/main/java",
            "E:/codes/xinlecai/xinlecai_server_springmvc/service/service-impl-rmq/src/main/java",
            "E:/codes/xinlecai/xinlecai_server_springmvc/service/xlc-ops-b/src/main/java",
    };
    public static String aimPath = "E:/codes/xlc-cloud/xlc-cloud-main/src/main/java";
    public static String sqlSourceFolder = "E:/codes/xinlecai/xinlecai_server_springmvc/service/service-impl/src/main/resources/conf/sqlMap";
    public static String sqlAimFolder = "E:/codes/xlc-cloud/xlc-cloud-main/src/main/resources/conf/sqlMap";
    
    public static void main(String[] args) throws IOException {
        try {
            Log.init();
            FileUpdateCheck.init();
            //
            for (int i = 0; i < sourceFolders.length; i++) {
                String sourceFolder = sourceFolders[i];
                dealSingle(sourceFolder, sourceFolder);
            }
            
            moveSqlMap(sqlSourceFolder);
            
            FileUpdateCheck.reWriteToFile();
        } catch (IOException e) {
            Log.write(e.getMessage() + "加载文件上次修改时间异常");
        } finally {
            Log.close();
        }
    }

    private static void moveSqlMap(String sqlSourceFolder) throws IOException {
        File file = new File(sqlSourceFolder);
        if(file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if(listFiles != null) {
                for (int i = 0; i < listFiles.length; i++) {
                    doCopy(listFiles[i], sqlAimFolder + "/" +listFiles[i].getName());
                }
            }
        }
    }

    private static void dealSingle(String sourceFolder, String sourceFile) throws IOException {
        File file = new File(sourceFile);
        if(file.exists()) {
            if(file.isFile()) {
                
                String sourcePath = file.getAbsolutePath();
                String fileRelativePath = sourcePath.substring(sourceFolder.length());
                doCopy(file, aimPath + fileRelativePath);
            } else if(file.isDirectory()) {
                String[] list = file.list();
                for (int i = 0; i < list.length; i++) {
                    dealSingle(sourceFolder, sourceFile + "/" + list[i]);
                }
            }
        }
    }

    private static void doCopy(File srcFile, String fileWriteToPath) throws IOException {
        boolean checkModified = FileUpdateCheck.isModified(srcFile);
        if(checkModified) {
            File fileCopy = new File(fileWriteToPath );
            
            try {
                FileUtils.copyFile(srcFile, fileCopy);
                Log.write(srcFile.getAbsolutePath() + " -> " + fileCopy.getAbsolutePath());
            } catch (IOException e) {
                Log.write(e.getMessage() + "复制失败: " + srcFile.getAbsolutePath());
            }
        }
    }

}
