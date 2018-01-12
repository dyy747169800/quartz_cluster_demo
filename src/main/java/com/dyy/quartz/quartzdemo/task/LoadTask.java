package com.dyy.quartz.quartzdemo.task;

import com.dyy.quartz.quartzdemo.entity.SystemTask;
import com.dyy.quartz.quartzdemo.service.SystemTaskService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 加载任务调度
 * @author: 段杨宇
 * @date: 15:35 2018/1/8
 *
 */
@Component
public class LoadTask {

    public static final Integer IS_YES = 1;//是、有效
    public static final Integer IS_NO = 0;//否，无效

    @Autowired
    private SystemTaskService systemTaskService;

    @Autowired
    private Scheduler scheduler;
    @PostConstruct
    public void loadTask() {
        List<SystemTask> tasks = this.systemTaskService.listAll();
        if (tasks.size() > 0) {
            for (SystemTask task : tasks) {
                // 任务开启状态 执行任务调度
                if (task.getStatus().intValue() != IS_NO) {
                    try {
                        String group = StringUtils.isEmpty(task.getGroup()) ? Scheduler.DEFAULT_GROUP : task.getGroup();
                        TriggerKey triggerKey = TriggerKey.triggerKey(task.getCode(),group);
                        // 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
                        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                        // 不存在，创建一个
                        if (null == cronTrigger) {
                            JobDetail jobDetail = JobBuilder.newJob(getClassByTask(task.getJobClass())).withIdentity(task.getCode(), Scheduler.DEFAULT_GROUP).requestRecovery().storeDurably().build();
                            // 添加任务参数
                            jobDetail.getJobDataMap().put("taskId", task.getId());
                               // 表达式调度构建器
                              //withMisfireHandlingInstructionDoNothing 所有被misfire的执行都被忽略掉，调度器会像平时一样等待下次调度  9点和10点的执行（misfire的2个）被立即执行，下次执行将在11点被准时执行。
                              //withMisfireHandlingInstructionFireAndProceed 立即执行第一次misfire的操作，并且放弃其他misfire的（类似所有misfire的操作被合并执行了）。然后继续按调度执行。无论misfire多少次trigger的执行，都只会立刻执行1次 9点和10点的被合并执行一次（换句话说，10点需要执行的那次，被pass了）。下次执行将在11点被准时执行
                             //withMisfireHandlingInstructionIgnoreMisfires 所有被misfire的执行会被立即执行，然后按照正常调度继续执行trigger。 9点和10点的被忽略掉，好像什么都没发生一样。下次执行将在11点被执行。
                            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression()).withMisfireHandlingInstructionDoNothing();
                            // 按新的cronExpression表达式构建一个新的trigger
                            cronTrigger = TriggerBuilder.newTrigger().withIdentity(task.getCode(),group).withSchedule(scheduleBuilder).build();

                            // 设置任务规则
                            // 调度任务
                            scheduler.scheduleJob(jobDetail, cronTrigger);
                        } else {
                            // Trigger已存在，那么更新相应的定时设置
                            // 表达式调度构建器
                            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression()).withMisfireHandlingInstructionDoNothing();

                            // 按新的cronExpression表达式重新构建trigger
                            cronTrigger = cronTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

                            // 按新的trigger重新设置job执行
                            scheduler.rescheduleJob(triggerKey, cronTrigger);
                        }
                        //如停果是暂状态则暂停任务
                        if(task.getStatus() == 2){
                            JobKey jobKey = JobKey.jobKey(task.getCode(), task.getGroup());
                            scheduler.pauseJob(jobKey);
                        }
                    } catch (SchedulerException | ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        }
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
}
