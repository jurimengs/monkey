package org.monkey.db.core;

import java.util.List;

/**
 * tableName， 会对应到一个 HashStore
 * datas， 会封装成一个数据对象， 数据对象就用 hashmap
 * @author zhouman
 *
 */
public class DataModel {
    private String tableName;
    private List<KeyValue> datas;
}
