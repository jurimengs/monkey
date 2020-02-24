package com.monkey.base;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class DataModelIn {
    private String tableName;
    private String primaryKey;
    private List<KeyValue> datas;
    private int version; //
    private Date operateTime;
}
