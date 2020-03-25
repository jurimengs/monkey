package code.move;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;


public class Log {
    private static String dateStr;

    private static FileWriter cacheWriter;
    
    public static void init() throws IOException {
        File file = new File(CodeMove.logFile);
        if(!file.exists()) {
            file.mkdirs();
        }
        cacheWriter = new FileWriter(file, true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateStr = sdf.format(new Date());
    }

    public static void write(String content) throws IOException {
        System.out.println(content);
//        cacheWriter.write("\n\n");
//        cacheWriter.write(dateStr + " => start\n");
        cacheWriter.write(dateStr + " => " + content + "\n");
//        cacheWriter.write(dateStr + " => end\n");
//        cacheWriter.flush();
    }
    
    public static void close() throws IOException {
        if(cacheWriter != null) {
            cacheWriter.close();
        }
    }
}
