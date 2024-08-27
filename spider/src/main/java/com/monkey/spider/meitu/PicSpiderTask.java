package com.monkey.spider.meitu;

import cn.hutool.core.util.URLUtil;
import com.monkey.spider.*;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Setter
public class PicSpiderTask {
    private String url; // String url = "http://www.meitu8.cc/MM/3/48959_7.html";
    private String localStoragePrefix; // 存储路径 前缀
    private int index; // 任务序号


    public void execute() {
        // 任意一个链接都 会下载其所有的 page （0 ~ N）

        URI uri = URLUtil.getHost(URLUtil.url(url));
        String host = uri.toString();

        Document document = PageUtil.getDocument(url);
        List<String> imgUrls = PageUtil.parse(document, host);
        // 生成文件夹 packageName
        String packageName = PageUtil.getPackageName(document);

        // 下载文件 imgUrls， 放到 packageName
        String downloadPackage = localStoragePrefix  + File.separator + packageName;
//        log.info("准备下载： {} 到 {}", url, downloadPackage);

        List<SingleDownloadModel> models = imgUrls.stream().map(imgurl -> {
            SingleDownloadModel tmp = new SingleDownloadModel();
            tmp.setFullHttpUrl(imgurl);
            tmp.setFileName(getFileName(imgurl));
            return tmp;
        }).collect(Collectors.toList());

        // 在目录下生成一个 txt 保存链接
        try {
            FileUtil.writeFile(url.getBytes(), new File("pageurl.txt"));
        } catch (FileNotFoundException e) {
            log.error("PicSpiderTask execute FileNotFoundException： {} 到 {}", url, downloadPackage, e);
        }
        ImgDownThread downThread = new ImgDownThread();
        downThread.setUrl(url);
        downThread.setSavePath(downloadPackage);
        downThread.setThreadName(downloadPackage);
        downThread.setModels(models);
        downThread.setIndex(index);
        ThreadPool.execute(downThread);
    }


    public void printUrl() {
        // 任意一个链接都 会下载其所有的 page （0 ~ N）

        URI uri = URLUtil.getHost(URLUtil.url(url));
        String host = uri.toString();

        Document document = PageUtil.getDocument(url);
        List<String> imgUrls = PageUtil.parse(document, host);
        imgUrls.stream().forEach(System.out::println);
    }


    private String getFileName(String url){
        String[] split = url.split("\\/");
        return split[split.length - 1];
    }
}
