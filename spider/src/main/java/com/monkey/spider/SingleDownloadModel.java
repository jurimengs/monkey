package com.monkey.spider;

import lombok.Data;

@Data
public class SingleDownloadModel {
    // 链接
    private String fullHttpUrl;
    private String fileName;
    //
    private int index;

}
