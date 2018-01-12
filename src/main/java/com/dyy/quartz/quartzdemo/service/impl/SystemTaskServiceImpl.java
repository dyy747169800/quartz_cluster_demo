package com.dyy.quartz.quartzdemo.service.impl;

import com.dyy.quartz.quartzdemo.dao.SystemTaskDao;
import com.dyy.quartz.quartzdemo.entity.SystemTask;
import com.dyy.quartz.quartzdemo.service.SystemTaskService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SystemTaskServiceImpl implements SystemTaskService {

    @Autowired
    private SystemTaskDao systemTaskDao;
    @Autowired
    private Scheduler scheduler;
    

    @Override
    public List<SystemTask> listAll() {
    	return this.systemTaskDao.listAll();
    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public Integer insertSystemTask(SystemTask systemTask) throws SchedulerException {
        systemTask.setStatus(SystemTask.STATUS_VALID);
    	this.systemTaskDao.insert(systemTask);
    	addJob(systemTask);
    	return systemTask.getId();
    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public Integer updateSystemTask(SystemTask systemTask) throws SchedulerException {
        int i = systemTaskDao.updateById(systemTask);
        updateJob(systemTask);
    	return i;
    }

    @Override
    public SystemTask selectSystemTask(SystemTask systemTask) {
        return systemTaskDao.selectBySystemTask(systemTask);
    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public Integer deleteSystemTask(SystemTask systemTask) throws SchedulerException {
        systemTask = this.systemTaskDao.selectBySystemTask(systemTask);
        if(systemTask !=null){
            deleteJob(systemTask);
            return systemTaskDao.deleteById(systemTask.getId());
        }
        return 0;
    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public Integer pauseSystemTask(SystemTask systemTask) throws SchedulerException {
        systemTask.setStatus(SystemTask.STATUS_PAUSE);
        int i = systemTaskDao.updateById(systemTask);
        pauseJob(systemTask);
        return 0;
    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public Integer resumeSystemTask(SystemTask systemTask) throws SchedulerException {
        systemTask.setStatus(SystemTask.STATUS_VALID);
        int i = systemTaskDao.updateById(systemTask);
        resumeJob(systemTask);
        return i;
    }

    private void resumeJob(SystemTask systemTask) throws SchedulerException {
        systemTask = systemTaskDao.selectBySystemTask(systemTask);
        JobKey jobKey = JobKey.jobKey(systemTask.getCode(), systemTask.getGroup());
        scheduler.resumeJob(jobKey);
    }

    private void pauseJob(SystemTask systemTask) throws SchedulerException {
        systemTask = systemTaskDao.selectBySystemTask(systemTask);
        JobKey jobKey = JobKey.jobKey(systemTask.getCode(), systemTask.getGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 立即执行job
     *
     */
    @Override
    public void runNowSystemTask(SystemTask systemTask) throws SchedulerException {
        systemTask = systemTaskDao.selectBySystemTask(systemTask);
        JobKey jobKey = JobKey.jobKey(systemTask.getCode(), systemTask.getGroup());
        scheduler.triggerJob(jobKey);
    }

    /**
     *
     * @param taskClassName
     *            任务执行类名
     * @return
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    private Class<? extends Job> getClassByTask(String taskClassName) throws ClassNotFoundException {
        return (Class<? extends Job>) Class.forName(taskClassName);
    }

    /**
     * 删除job
     */
    private void deleteJob(SystemTask systemTask) throws SchedulerException {
        systemTask = this.systemTaskDao.selectById(systemTask.getId());
        JobKey jobKey = JobKey.jobKey(systemTask.getCode(), StringUtils.isEmpty(systemTask.getGroup())?Scheduler.DEFAULT_GROUP:systemTask.getGroup());
        scheduler.deleteJob(jobKey);
    }

    /**
     *添加job
     */
    private void addJob(SystemTask systemTask) throws SchedulerException {
        systemTask = this.systemTaskDao.selectById(systemTask.getId());
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(systemTask.getCode(), systemTask.getGroup());
            // 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
            CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 不存在，创建一个
            if (null == cronTrigger) {
                JobDetail jobDetail = JobBuilder.newJob(getClassByTask(systemTask.getJobClass())).withIdentity(systemTask.getCode(), systemTask.getGroup()).requestRecovery().storeDurably().build();
                // 添加任务参数
                jobDetail.getJobDataMap().put("taskId", systemTask.getId());
                // 表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(systemTask.getCronExpression()).withMisfireHandlingInstructionDoNothing();
                // 按新的cronExpression表达式构建一个新的trigger
                cronTrigger = TriggerBuilder.newTrigger().withIdentity(systemTask.getCode(), systemTask.getGroup()).withSchedule(scheduleBuilder).build();
                // 调度任务
                scheduler.scheduleJob(jobDetail, cronTrigger);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SchedulerException();
        }
    }

    /**
     *更新job
     */
    private void updateJob(SystemTask systemTask) throws SchedulerException {
        systemTask = this.systemTaskDao.selectById(systemTask.getId());
        TriggerKey triggerKey = TriggerKey.triggerKey(systemTask.getCode(), Scheduler.DEFAULT_GROUP);
        // 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if(null != cronTrigger){
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(systemTask.getCronExpression());

            // 按新的cronExpression表达式重新构建trigger
            cronTrigger = cronTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, cronTrigger);
        }
    }
}

