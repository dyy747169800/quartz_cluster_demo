package com.dyy.quartz.quartzdemo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

@Configuration
/**
 *  @ImportResource(locations={"classpath:applicationContext.xml"})
 *  使用配置文件方式打开这行
 */
public class QuartzConfig {


    @Autowired
    private DruidDataSource dataSource;

    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        Resource resource = new ClassPathResource("application.properties");
        schedulerFactoryBean.setConfigLocation(resource);
        return schedulerFactoryBean;
    }


}