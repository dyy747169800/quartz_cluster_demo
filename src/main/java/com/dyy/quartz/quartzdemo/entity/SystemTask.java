package com.dyy.quartz.quartzdemo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务
 *
 * @author 段杨宇
 * @create 2018-01-03 10:44
 **/
public class SystemTask implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     * 无效
     */
    public static final Integer STATUS_INVALID = 0;
    /**
     * 有效
     */
    public static final Integer STATUS_VALID = 1;
    /**
     * 暂停
     */
    public static final Integer STATUS_PAUSE = 2;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 任务编码
     */
    private String code;
    /**
     * 任务名称
     */
    private String name;
    /**
     * 任务分组
     */
    private String group;
    /**
     * 调度任务类
     */
    private String jobClass;
    /**
     * 触发规则
     */
    private String cronExpression;
    /**
     * 状态：0-无效, 1-有效，,2-暂停
     */
    private Integer status;
    /**
     * 描述
     */
    private String remark;
    /**
     * 创建人id
     */
    private Integer createId;
    /**
     * 创建人
     */
    private String createName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人
     */
    private Integer modifyId;
    /**
     * 修改人姓名
     */
    private String modifyName;
    /**
     * 修改时间
     */
    private Date modifyTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getModifyId() {
        return modifyId;
    }

    public void setModifyId(Integer modifyId) {
        this.modifyId = modifyId;
    }

    public String getModifyName() {
        return modifyName;
    }

    public void setModifyName(String modifyName) {
        this.modifyName = modifyName;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
