package com.dyy.quartz.quartzdemo.task;


import com.dyy.quartz.quartzdemo.service.SimpleService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution// 不允许并发执行
public class MyQuartzJob extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(MyQuartzJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
        SimpleService simpleService = getApplicationContext(jobexecutioncontext).getBean(SimpleService.class);
        simpleService.simpleMethod();

    }

    private ApplicationContext getApplicationContext(final JobExecutionContext jobexecutioncontext) {
        try {
            return (ApplicationContext) jobexecutioncontext.getScheduler().getContext().get("applicationContext");
        } catch (SchedulerException e) {
            logger.error("jobexecutioncontext.getScheduler().getContext() error!", e);
            throw new RuntimeException(e);
        }
    }

}