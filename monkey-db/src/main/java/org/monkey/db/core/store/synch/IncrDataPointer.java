package org.monkey.db.core.store.synch;

import java.util.List;

import lombok.Data;

@Data
public class IncrDataPointer {
    private String ip; // 目标ip
    private String tableName; // 数据仓库对应的表名
    private List<IncrDataLinkPointer> incrDataLinkPointers; // 该仓库需要从哪里开始同步
    
}
