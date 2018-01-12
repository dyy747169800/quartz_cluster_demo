package com.dyy.quartz.quartzdemo.dao;

import com.dyy.quartz.quartzdemo.entity.SystemTask;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 段杨宇
 * @create 2018-01-03 10:47
 **/
@Repository
public interface SystemTaskDao {
    int updateById(SystemTask systemTask);

    SystemTask selectBySystemTask(SystemTask systemTask);

    int insert(SystemTask vo);

    int deleteById(Integer id);

    SystemTask selectById(Integer id);

    List<SystemTask> listAll();

}
