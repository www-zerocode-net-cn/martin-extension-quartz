package com.java2e.martin.extension.quartz.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java2e.martin.common.core.api.R;
import com.java2e.martin.common.data.mybatis.page.MartinPage;
import com.java2e.martin.extension.quartz.dto.QuartzConfigDTO;
import com.java2e.martin.extension.quartz.mapper.QuartzMapper;
import com.java2e.martin.extension.quartz.service.QuartzJobService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 狮少
 * @version 1.0
 * @date 2021/3/12
 * @describtion QuartzJobServiceImpl
 * @since 1.0
 */
@Service
@Slf4j
public class QuartzJobServiceImpl implements QuartzJobService, ApplicationContextAware {
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private QuartzMapper quartzMapper;

    private ApplicationContext applicationContext;

    @SneakyThrows
    @Override
    public R addJob(QuartzConfigDTO configDTO) {
        String jobName = configDTO.getJobName();
        String groupName = configDTO.getGroupName();
        String springJobClass = configDTO.getJobClass();
        String cronExpression = configDTO.getCronExpression();
        Map<String, Object> param = configDTO.getParam();
        //构建job信息
        Class<? extends Job> jobClass = (Class<? extends Job>) applicationContext.getType(springJobClass);
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, groupName).build();
        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, groupName).withSchedule(scheduleBuilder).build();
        //获得JobDataMap，写入数据
        if (param != null) {
            trigger.getJobDataMap().putAll(param);
        }
        scheduler.scheduleJob(jobDetail, trigger);
        return R.ok("任务[" + jobName + "]添加成功");
    }

    @SneakyThrows
    @Override
    public R pauseJob(String jobName, String groupName) {
        scheduler.pauseJob(JobKey.jobKey(jobName, groupName));
        return R.ok("任务[" + jobName + "]暂停成功");
    }

    @SneakyThrows
    @Override
    public R resumeJob(String jobName, String groupName) {
        scheduler.resumeJob(JobKey.jobKey(jobName, groupName));
        return R.ok("任务[" + jobName + "]恢复成功");
    }

    @SneakyThrows
    @Override
    public R runOnce(String jobName, String groupName) {
        scheduler.triggerJob(JobKey.jobKey(jobName, groupName));
        return R.ok("任务[" + jobName + "]立即运行一次成功");
    }

    @SneakyThrows
    @Override
    public R updateJob(QuartzConfigDTO configDTO) {
        String jobName = configDTO.getJobName();
        String groupName = configDTO.getGroupName();
        String springJobClass = configDTO.getJobClass();
        String cronExpression = configDTO.getCronExpression();
        Map<String, Object> param = configDTO.getParam();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, groupName);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (cronExpression != null) {
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        }
        //修改map
        if (param != null) {
            trigger.getJobDataMap().putAll(param);
        }
        // 按新的trigger重新设置job执行
        scheduler.rescheduleJob(triggerKey, trigger);
        return R.ok("任务[" + jobName + "]更新成功");
    }

    @SneakyThrows
    @Override
    public R deleteJob(String jobName, String groupName) {
        //暂停、移除、删除
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobName, groupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobName, groupName));
        scheduler.deleteJob(JobKey.jobKey(jobName, groupName));
        return R.ok("任务[" + jobName + "]删除成功");
    }

    @SneakyThrows
    @Override
    public R startAllJobs() {
        scheduler.start();
        return R.ok("所有任务启动成功");
    }

    @SneakyThrows
    @Override
    public R pauseAllJobs() {
        scheduler.pauseAll();
        return R.ok("所有任务暂停成功");
    }

    @SneakyThrows
    @Override
    public R resumeAllJobs() {
        scheduler.resumeAll();
        return R.ok("所有任务恢复成功");
    }

    @SneakyThrows
    @Override
    public R shutdownAllJobs() {
        if (!scheduler.isShutdown()) {
            // 需谨慎操作关闭scheduler容器
            // scheduler生命周期结束，无法再 start() 启动scheduler
            scheduler.shutdown(true);
        }
        return R.ok("所有任务关闭成功");
    }

    @Override
    public Page getPage(Map<String, Object> param) {
        MartinPage<HashMap> page = new MartinPage(param, new HashMap());
        return page.setRecords(quartzMapper.getPage(page, page.getCondition()));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
