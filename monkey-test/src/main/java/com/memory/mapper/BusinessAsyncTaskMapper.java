package com.memory.mapper;


import com.memory.entity.BusinessAsyncTask;

import java.util.List;

/**
 * 任务状态Mapper接口
 * 
 * @author ${author}
 * @date 2024-04-11
 */
public interface BusinessAsyncTaskMapper {
    /**
     * 查询任务状态
     * 
     * @return 任务状态
     */
    public BusinessAsyncTask selectBusinessAsyncTaskById(Long id);

    /**
     * 查询任务状态列表
     * 
     * @return 任务状态集合
     */
    public List<BusinessAsyncTask> selectBusinessAsyncTaskList(BusinessAsyncTask businessAsyncTask);

    /**
     * 新增任务状态
     * 
     * @return 结果
     */
    public int insert(BusinessAsyncTask businessAsyncTask);

    /**
     * 修改任务状态
     * 
     * @return 结果
     */
    public int update(BusinessAsyncTask businessAsyncTask);

    /**
     * 删除任务状态
     * 
     * @return 结果
     */
    public int deleteBusinessAsyncTaskById(Long id);

    /**
     * 批量删除任务状态
     * 
     * @return 结果
     */
    public int deleteBusinessAsyncTaskByIds(Long[] ids);
}
