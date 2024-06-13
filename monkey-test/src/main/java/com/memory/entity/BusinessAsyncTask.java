package com.memory.entity;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 任务状态对象 business_async_task
 * 
 * @author ${author}
 * @date 2024-04-11
 */
@Data
public class BusinessAsyncTask {
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 任务名称 */
    private String taskName;

    /** 任务状态 1: 执行中 2:结束 3: 异常 */
    private Integer status;

    /** 是否删除 0:否 1:是 */
    private Boolean deleted;

    /** 事件操作人id */
    private Long creator;

    /** 事件更新人id */
    private Long updater;

    /** 租户id */
    private Long tenantId;



}
