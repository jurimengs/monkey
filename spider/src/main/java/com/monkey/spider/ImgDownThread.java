package com.monkey.spider;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ImgDownThread implements Runnable {

    @Setter
    @Getter
    private String url;
    @Setter
    @Getter
    private String threadName;
    @Setter
    @Getter
    private String savePath;
    @Setter
    @Getter
    private int index; // 任务序号
    @Setter
    @Getter
    // 完成的下载路径
    private List<SingleDownloadModel> models;

    private int redownTimes = 0;
    
    @Override
    public void run() {
        List<DownResult> faileds = new ArrayList();
        for(SingleDownloadModel model : models) {
            String fullUrl = model.getFullHttpUrl();
            fullUrl = StringUtils.replace(fullUrl,"https", "http");

            String fileName = model.getFileName();
            if(StringUtils.isNotBlank(fileName)) {
                fileName = model.getFileName();
            } else {
                fileName = String.format("%06d", model.getIndex(), ".ts");
            }
//            try {
//                int sleep = RandomUtils.nextInt(0, 500);
//                Thread.currentThread().sleep(sleep);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }

            DownResult result = JsoupUtil.jsoupDownLoad(fullUrl, fileName, savePath);
            if(!result.isOk()) {
                faileds.add(result);
            }
        }

        if(CollectionUtils.isNotEmpty(faileds)) {
            reDownload(faileds);
        }
        log.info("任务序号 {} 任务名称 {} 下载完成", index, threadName);
    }

    private void reDownload(List<DownResult> faileds) {
        if(redownTimes <= 3) {
            redownTimes ++;
            List<DownResult> collect = faileds.stream().map(failed -> {
                        String imgUrl = failed.getImgUrl();
                        String savePath = failed.getSavePath();
                        String fileName = failed.getFileName();
                        DownResult result = JsoupUtil.jsoupDownLoad(imgUrl, fileName, savePath);
                        return result;
                    })
                    .filter(DownResult::isFail)
                    .collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(collect)) {
                reDownload(collect);
            }
        } else {
            log.error("多次尝试下载仍存在失败的文件, 下载 url: {} ", url);
            log.error("多次尝试下载仍存在失败的文件, 失败文件 {} ", JSON.toJSONString(faileds));
        }
    }

}
