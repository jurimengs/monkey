package com.monkey.base;

import java.util.Date;
import java.util.Map;

import lombok.Data;

@Data
public class DataModelOut {
    private String tableName;
    private Map<String, Object> datas;
    private int version; //
    private Date operateTime;
}
