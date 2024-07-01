package com.core.events.builder;

import com.constant.Constant;

public class ScreenCaptureConfig extends Config{
    private int leftx;
    private int lefty;
    private int width;
    private int height;
    private String tempName;
    //
    public ScreenCaptureConfig(int leftx, int lefty, int width, int height, String tempName) {
        super(Constant.Event.ACTIVE_WINDOW_DELAY);
        this.leftx = leftx;
        this.lefty = lefty;
        this.width = width;
        this.height = height;
        this.tempName = tempName;
    }

    public int getLeftx() {
        return leftx;
    }

    public void setLeftx(int leftx) {
        this.leftx = leftx;
    }

    public int getLefty() {
        return lefty;
    }

    public void setLefty(int lefty) {
        this.lefty = lefty;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }
}
