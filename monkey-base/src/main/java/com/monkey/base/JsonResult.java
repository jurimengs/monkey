package com.monkey.base;

import java.io.Serializable;
import java.util.List;

public class JsonResult<T> implements Serializable  {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // CommonStatus.JsonResult 0:成功 1:失败
    private int code;
    private String msg;
    private String extra;
    // 总数量
    private int total;
    // 总页数
    private int totalpage;
    private List<T> data;
    private T result; // 特殊用途,一般不用
    
    public void addData(T element) {
        data.add(element);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }


}
