package com.dyy.quartz.quartzdemo.service;

import com.dyy.quartz.quartzdemo.entity.SystemTask;
import org.quartz.SchedulerException;

import java.util.List;

public interface SystemTaskService {
    /**
     * 查询任务列表
     * @return
     */
	List<SystemTask> listAll();

    /**
     * 新增任务
     * @author: 段杨宇
     * @date: 10:44 2018/1/10
     * @param systemTask
     * @return
     * @throws SchedulerException
     */
    Integer insertSystemTask(SystemTask systemTask) throws SchedulerException;

    /**
     * 更新任务
     * @author: 段杨宇
     * @date: 10:44 2018/1/10
     * @param systemTask
     * @return
     * @throws SchedulerException
     */
    Integer updateSystemTask(SystemTask systemTask) throws SchedulerException;

    /**
     * 查找任务
     * @author: 段杨宇
     * @date: 10:41 2018/1/10
     * @param systemTask
     * @return
     */
    SystemTask selectSystemTask(SystemTask systemTask);

    /**
     * 删除任务
     * @author: 段杨宇
     * @date: 10:41 2018/1/10
     * @param systemTask
     * @return
     * @throws SchedulerException
     */
    Integer deleteSystemTask(SystemTask systemTask) throws SchedulerException;

    /**
     * 暂停任务
     * @author: 段杨宇
     * @date: 10:41 2018/1/10
     * @param systemTask
     * @return
     * @throws SchedulerException
     */
    Integer pauseSystemTask(SystemTask systemTask) throws SchedulerException;

    /**
     * 恢复任务
     * @author: 段杨宇
     * @date: 10:40 2018/1/10
     * @param systemTask
     * @return
     * @throws SchedulerException
     */
    Integer resumeSystemTask(SystemTask systemTask) throws SchedulerException;

    /**
     * 立即执行一次任务
     * @author: 段杨宇
     * @date: 10:39 2018/1/10
     * @param systemTask
     * @throws SchedulerException
     */
    void runNowSystemTask(SystemTask systemTask) throws SchedulerException;
}

