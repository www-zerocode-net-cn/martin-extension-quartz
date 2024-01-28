package com.java2e.martin.extension.quartz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java2e.martin.common.core.api.R;
import com.java2e.martin.extension.quartz.dto.QuartzConfigDTO;

import java.util.Map;

/**
 * @author 狮少
 * @version 1.0
 * @date 2021/3/12
 * @describtion QuartzJobService
 * @since 1.0
 */
public interface QuartzJobService {
    /**
     * 添加任务可以传参数
     *
     * @param clazzName
     * @param jobName
     * @param groupName
     * @param cronExp
     * @param param
     */
    R addJob(QuartzConfigDTO configDTO);

    /**
     * 暂停任务
     *
     * @param jobName
     * @param groupName
     */
    R pauseJob(String jobName, String groupName);

    /**
     * 恢复任务
     *
     * @param jobName
     * @param groupName
     */
    R resumeJob(String jobName, String groupName);

    /**
     * 立即运行一次定时任务
     *
     * @param jobName
     * @param groupName
     */
    R runOnce(String jobName, String groupName);

    /**
     * 更新任务
     *
     * @param jobName
     * @param groupName
     * @param cronExp
     * @param param
     */
    R updateJob(QuartzConfigDTO configDTO);

    /**
     * 删除任务
     *
     * @param jobName
     * @param groupName
     */
    R deleteJob(String jobName, String groupName);

    /**
     * 启动所有任务
     */
    R startAllJobs();

    /**
     * 暂停所有任务
     */
    R pauseAllJobs();

    /**
     * 恢复所有任务
     */
    R resumeAllJobs();

    /**
     * 关闭所有任务
     */
    R shutdownAllJobs();

    /**
     * 分页获取调度任务
     *
     * @param page
     * @return
     */
    Page getPage(Map<String, Object> page);
}
