package com.monkey.spider;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.*;

@Slf4j
public class JsoupUtil {

    public static DownResult jsoupDownLoad(String imgUrl, String fileName, String savePath) {
        DownResult result = new DownResult();
        result.setFileName(fileName);
        result.setImgUrl(imgUrl);
        result.setSavePath(savePath);
        try {

            result.setOk(Boolean.TRUE);
            // 文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            if (file.exists()) {
//                log.error("已存在文件, 跳过: " + file.getAbsolutePath());
            } else {
                Connection.Response response = Jsoup.connect(imgUrl).timeout(4000).userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15").ignoreContentType(true).execute();
                BufferedInputStream bufferedInputStream = response.bodyStream();
                saveFile(bufferedInputStream, file);//保存文件的地址

            }

        } catch (Exception e) {
            log.error("jsoupDownLoad err {}, fileName {}, imgUrl {}, savePath {}", e.getMessage(), fileName , imgUrl , savePath);
            result.setOk(Boolean.FALSE);
            result.setErrmsg("jsoupDownLoad err : " + e.getMessage());
        }
        return result;

    }
    /**
     * 保存文件到本地
     * @param bufferedInputStream
     */
    public static void saveFile(BufferedInputStream bufferedInputStream, File toSave) throws IOException {
        //一次最多读取1k
        byte[] buffer = new byte[1024];
        //实际读取的长度
        int readLenghth;
        //根据文件保存地址，创建文件输出流
        FileOutputStream fileOutputStream = new FileOutputStream(toSave);
        //创建的一个写出的缓冲流
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            //文件逐步写入本地
            while ((readLenghth = bufferedInputStream.read(buffer,0,1024)) != -1){//先读出来，保存在buffer数组中
                bufferedOutputStream.write(buffer,0,readLenghth);//再从buffer中取出来保存到本地
            }
            //关闭缓冲流
            bufferedOutputStream.close();
        } catch (IOException e) {
            log.error("saveFile err ", e);
        } finally {
            if(fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    log.error("saveFile fileOutputStream.close err", e);
                }
            }
            if(bufferedInputStream != null) {

                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    log.error("saveFile bufferedInputStream.close err", e);
                }
            }
        }


    }

}
