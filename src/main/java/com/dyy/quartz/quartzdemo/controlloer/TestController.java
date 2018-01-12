package com.dyy.quartz.quartzdemo.controlloer;

import com.dyy.quartz.quartzdemo.Constant;
import com.dyy.quartz.quartzdemo.entity.SystemTask;
import com.dyy.quartz.quartzdemo.service.SystemTaskService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试
 *
 * @author 段杨宇
 * @create 2018-01-03 10:54
 **/
@RequestMapping("systemTask")
@Controller
public class TestController {

    @Autowired
    private SystemTaskService systemTaskService;

    @RequestMapping("/ping")
    public String ping(){
        return "pong";
    }

    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView view = new ModelAndView("index");
        view.addObject("aaa",2);
        return view;
    }

    /**
     * 列表
     * @author: 段杨宇
     * @date: 13:40 2018/1/10
     * @return
     * @throws SchedulerException
     */
    @RequestMapping("/list")
    public ModelAndView list() throws SchedulerException {
        ModelAndView view = new ModelAndView("list");
        List<SystemTask> systemTaskList = systemTaskService.listAll();
        view.addObject("systemTaskList",systemTaskList);
        return view;
    }

    /**
     * @author: 段杨宇
     * @date: 13:38 2018/1/10
     * @param systemTask
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public Map<String,Object> deleteSystemTask(SystemTask systemTask){
        Map<String,Object> data = new HashMap<>();
        try {
            systemTaskService.deleteSystemTask(systemTask);
            data.put(Constant.ERROR_CODE_KEY,Constant.ERROR_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            data.put(Constant.ERROR_CODE_KEY,Constant.ERROR_CODE_FAIL);
        }
        return data;
    }


    /**
     * 新增或者修改任务
     * @author: 段杨宇
     * @date: 13:38 2018/1/10
     * @param systemTask
     * @return
     */
    @ResponseBody
    @RequestMapping("/addOrUpdate")
    public Map<String,Object> addOrUpdateSystemTask(SystemTask systemTask){
        Map<String,Object> data = new HashMap<>();
        try {
            if(null != systemTask.getId()){
                systemTaskService.updateSystemTask(systemTask);
            }else {
                systemTaskService.insertSystemTask(systemTask);
            }
            data.put(Constant.ERROR_CODE_KEY,Constant.ERROR_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            data.put(Constant.ERROR_CODE_KEY,Constant.ERROR_CODE_FAIL);
        }
        return data;
    }

    /**
     * 暂停任务
     * @author: 段杨宇
     * @date: 13:36 2018/1/10
     * @param systemTask
     * @return
     */
    @ResponseBody
    @RequestMapping("/pause")
    public Map<String,Object> pauseSystemTask(SystemTask systemTask){
        Map<String,Object> data = new HashMap<>();
        try {
            systemTaskService.pauseSystemTask(systemTask);
            data.put(Constant.ERROR_CODE_KEY,Constant.ERROR_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            data.put(Constant.ERROR_CODE_KEY,Constant.ERROR_CODE_FAIL);
        }
        return data;
    }

    /**
     * 恢复任务
     * @author: 段杨宇
     * @date: 13:37 2018/1/10
     * @param systemTask
     * @return
     */
    @ResponseBody
    @RequestMapping("/resume")
    public Map<String,Object> resumeSystemTask(SystemTask systemTask){
        Map<String,Object> data = new HashMap<>();
        try {
            systemTaskService.resumeSystemTask(systemTask);
            data.put(Constant.ERROR_CODE_KEY,Constant.ERROR_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            data.put(Constant.ERROR_CODE_KEY,Constant.ERROR_CODE_FAIL);
        }
        return data;
    }

    /**
     * 触发一次
     * @author: 段杨宇
     * @date: 17:19 2018/1/10
     * @param systemTask
     * @return
     */
    @ResponseBody
    @RequestMapping("/runNow")
    public Map<String,Object> runNowSystemTask(SystemTask systemTask){
        Map<String,Object> data = new HashMap<>();
        try {
            systemTaskService.runNowSystemTask(systemTask);
            data.put(Constant.ERROR_CODE_KEY,Constant.ERROR_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            data.put(Constant.ERROR_CODE_KEY,Constant.ERROR_CODE_FAIL);
        }
        return data;
    }
}
