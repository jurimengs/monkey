package code.move;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class CodeMove {
    private static String relativePath = "src/main/java";
    private static String aimPath = "E:/codes/xlc-cloud/xlc-cloud-main/" + relativePath;
    
    public static void main(String[] args) {
        String[] sourceFolders = {
                "E:/codes/xinlecai/xinlecai_server_springmvc/service/service-api/",
                "E:/codes/xinlecai/xinlecai_server_springmvc/service/service-impl/",
                "E:/codes/xinlecai/xinlecai_server_springmvc/service/service-impl-rmq/",
                "E:/codes/xinlecai/xinlecai_server_springmvc/service/xlc-ops-b/"
        };
        
        try {
            FileUpdateCheck.init();
        } catch (IOException e) {
            System.out.println(e.getMessage() + "加载文件上次修改时间异常");
            return;
        }
        
        for (int i = 0; i < sourceFolders.length; i++) {
            String sourceFolder = sourceFolders[i] + relativePath;
            dealSingle(sourceFolder, sourceFolder);
        }
    }

    private static void dealSingle(String sourceFolder, String sourceFile) {
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

    private static void doCopy(File srcFile, String fileWriteToPath) {
        boolean checkModified = FileUpdateCheck.isModified(srcFile);
        if(checkModified) {
            File fileCopy = new File(fileWriteToPath );
            
            try {
                FileUtils.copyFile(srcFile, fileCopy);
                System.out.println(srcFile.getAbsolutePath() + " -> " + fileCopy.getAbsolutePath());
            } catch (IOException e) {
                System.out.println(e.getMessage() + "复制失败: " + srcFile.getAbsolutePath());
            }
        }
    }
}
