package com.monkey.spider;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DownResult {
    private String fileName;
    private String imgUrl;
    private String savePath;
    private boolean ok;
    private String errmsg;
    public boolean isFail() {
        return !ok;
    }
}
