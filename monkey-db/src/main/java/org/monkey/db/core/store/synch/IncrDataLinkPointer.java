package org.monkey.db.core.store.synch;

import org.monkey.db.core.store.Link;

import lombok.Data;

@Data
public class IncrDataLinkPointer {
    private Link link; // 待同步的数据仓库
    private int incrPointer; // 该仓库需要从哪里开始同步
    
}
