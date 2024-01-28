package com.java2e.martin.extension.quartz.config;

import com.java2e.martin.extension.quartz.listen.MartinJobListener;
import com.java2e.martin.extension.quartz.listen.MartinSchedulerListener;
import com.java2e.martin.extension.quartz.listen.MartinTriggerListener;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.EverythingMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;

/**
 * @author 狮少
 * @version 1.0
 * @date 2021/3/12
 * @describtion QuartzConfig
 * @since 1.0
 */
@Configuration
@Slf4j
public class QuartzConfig {
    @Autowired
    private MartinSchedulerListener martinSchedulerListener;

    @Autowired
    private MartinJobListener martinJobListener;

    @Autowired
    private MartinTriggerListener martinTriggerListener;

    /**
     * 通过SchedulerFactoryBean获取Scheduler的实例
     *
     * @return
     * @throws IOException
     * @throws SchedulerException
     */
    @Bean(name = "scheduler")
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) throws IOException, SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        //全局添加监听器
        //添加SchedulerListener监听器
        scheduler.getListenerManager().addSchedulerListener(martinSchedulerListener);
        // 添加JobListener, 支持带条件匹配监听器
        scheduler.getListenerManager().addJobListener(martinJobListener, EverythingMatcher.allJobs());
        // 添加triggerListener，设置全局监听
        scheduler.getListenerManager().addTriggerListener(martinTriggerListener, EverythingMatcher.allTriggers());
        return scheduler;
    }
}
