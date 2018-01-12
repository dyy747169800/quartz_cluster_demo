package com.dyy.quartz.quartzdemo.service.impl;

import com.dyy.quartz.quartzdemo.service.SimpleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 测试
 *
 * @author 段杨宇
 * @create 2017-12-29 11:14
 **/
@Service
public class SimpleServiceImpl implements SimpleService,Serializable {
    private static final long serialVersionUID = 122323233244334343L;
    private static final Logger logger = Logger.getLogger(SimpleServiceImpl.class);

    @Override
    public void simpleMethod(){
        //这里执行定时调度业务
        logger.info("触发任务");
    }
}
