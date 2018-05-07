package com.dyy.quartz.quartzdemo.task.plugin;

import org.quartz.*;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Date;

/**
 * quartz触发日志
 *
 * @author 段杨宇
 * @create 2018-02-24 13:26
 **/
public class LoggingTriggerHistoryPlugin implements SchedulerPlugin,TriggerListener {

    private String name;

    /**
     * triggerb被触发将要执行execute()消息
     */
    private String triggerFiredMessage = "定时任务【{0}】被触发, 当前触发时间 {4, date,yyyy-MM-dd HH:mm:ss} ,下一次触发 {3, date,yyyy-MM-dd HH:mm:ss}";

    /**
     * trigger错过触发消息
     */
    private String triggerMisfiredMessage = "定时任务【{0}】错过触发, 当前触发时间 {4, date,yyyy-MM-dd HH:mm:ss} ,下一次触发时间 {3, date,yyyy-MM-dd HH:mm:ss}";

    /**
     * trigger被触发完成消息
     */
    private String triggerCompleteMessage = "定时任务【{0}】触发完成, 当前触发时间 {4, date,yyyy-MM-dd HH:mm:ss} ,下一次触发时间 {3, date,yyyy-MM-dd HH:mm:ss} 触发器指令: {9}";

    private final Logger log = LoggerFactory.getLogger(getClass());


    public LoggingTriggerHistoryPlugin() {
    }


    protected Logger getLog() {
        return log;
    }

    @Override
    public void start() {

    }

    @Override
    public void shutdown() {

    }

    public String getTriggerCompleteMessage() {
        return triggerCompleteMessage;
    }

    public String getTriggerFiredMessage() {
        return triggerFiredMessage;
    }

    public String getTriggerMisfiredMessage() {
        return triggerMisfiredMessage;
    }

    public void setTriggerCompleteMessage(String triggerCompleteMessage) {
        this.triggerCompleteMessage = triggerCompleteMessage;
    }

    public void setTriggerFiredMessage(String triggerFiredMessage) {
        this.triggerFiredMessage = triggerFiredMessage;
    }

    public void setTriggerMisfiredMessage(String triggerMisfiredMessage) {
        this.triggerMisfiredMessage = triggerMisfiredMessage;
    }

    @Override
    public void initialize(String pname, Scheduler scheduler, ClassLoadHelper classLoadHelper)
            throws SchedulerException {
        this.name = pname;

        scheduler.getListenerManager().addTriggerListener(this,  EverythingMatcher.allTriggers());
    }

    @Override
    public String getName() {
        return name;
    }


    /**
     *  Trigger 被触发了，此时Job 上的 execute() 方法将要被执行
     * @author: 段杨宇
     * @date: 13:39 2018/2/24
     * @param trigger
     * @param context
     */
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        if (!getLog().isInfoEnabled()) {
            return;
        }

        Object[] args = {
                trigger.getKey().getName(), trigger.getKey().getGroup(),
                trigger.getPreviousFireTime(), trigger.getNextFireTime(),
                new Date(), context.getJobDetail().getKey().getName(),
                context.getJobDetail().getKey().getGroup(),
                context.getRefireCount()
        };
        getLog().info(MessageFormat.format(getTriggerFiredMessage(), args));
    }

    /**
     * 当前Trigger触发错过了
     * @author: 段杨宇
     * @date: 13:34 2018/2/24
     * @param trigger
     */
    @Override
    public void triggerMisfired(Trigger trigger) {
        if (!getLog().isInfoEnabled()) {
            return;
        }

        Object[] args = {
                trigger.getKey().getName(), trigger.getKey().getGroup(),
                trigger.getPreviousFireTime(), trigger.getNextFireTime(),
                new Date(), trigger.getJobKey().getName(),
                trigger.getJobKey().getGroup()
        };
        getLog().info(MessageFormat.format(getTriggerMisfiredMessage(), args));
    }

    /**
     * Trigger 被触发并且完成了 Job 的执行,此方法被调用
     * @author: 段杨宇
     * @date: 13:34 2018/2/24
     * @param trigger
     * @param context
     * @param triggerInstructionCode
     */
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context,Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        if (!getLog().isInfoEnabled()) {
            return;
        }

        String instrCode = "UNKNOWN";
        if (triggerInstructionCode == Trigger.CompletedExecutionInstruction.DELETE_TRIGGER) {
            instrCode = "删除触发器";
        } else if (triggerInstructionCode == Trigger.CompletedExecutionInstruction.NOOP) {
            instrCode = "正常执行,无操作";
        } else if (triggerInstructionCode == Trigger.CompletedExecutionInstruction.RE_EXECUTE_JOB) {
            instrCode = "重新执行任务";
        } else if (triggerInstructionCode == Trigger.CompletedExecutionInstruction.SET_ALL_JOB_TRIGGERS_COMPLETE) {
            instrCode = "设置所有触发器完成";
        } else if (triggerInstructionCode == Trigger.CompletedExecutionInstruction.SET_TRIGGER_COMPLETE) {
            instrCode = "设置当前触发器完成";
        }
        Object[] args = {
                trigger.getKey().getName(),trigger.getKey().getGroup(),
                trigger.getPreviousFireTime(),trigger.getNextFireTime(),
                new Date(),context.getJobDetail().getKey().getName(),
                context.getJobDetail().getKey().getGroup(),context.getRefireCount(),
                triggerInstructionCode.toString(),instrCode
        };
        getLog().info(MessageFormat.format(getTriggerCompleteMessage(), args));
    }

    /**
     * 发现此次Job的相关资源准备存在问题，不便展开任务，返回true表示否决此次任务执行 false表示继续执行
     * @author: 段杨宇
     * @date: 13:39 2018/2/24
     * @param trigger
     * @param context
     * @return
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

}
