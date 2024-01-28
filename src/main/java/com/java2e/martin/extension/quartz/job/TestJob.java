package com.java2e.martin.extension.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 狮少
 * @version 1.0
 * @date 2021/3/12
 * @describtion TestJob, 测试任务定时任务类，采用spring bean模式
 * @since 1.0
 */
@Component
@Slf4j
public class TestJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        try {
            log.info(context.getScheduler().getSchedulerInstanceId() + "--" + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));
        } catch (SchedulerException e) {
            log.error("任务执行失败", e);
        }
    }
}
