package com.java2e.martin.extension.quartz.controller;

import com.java2e.martin.common.core.api.R;
import com.java2e.martin.common.core.support.ValidateGroup;
import com.java2e.martin.extension.quartz.dto.QuartzConfigDTO;
import com.java2e.martin.extension.quartz.service.QuartzJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 狮少
 * @version 1.0
 * @date 2021/3/12
 * @describtion QuartzController
 * @since 1.0
 */
@RestController
@RequestMapping("/quartz")
@Slf4j
public class QuartzController {
    @Autowired
    private QuartzJobService quartzJobService;

    /**
     * 添加新任务
     *
     * @param configDTO
     * @return
     */
    @PostMapping("/addJob")
    public R addJob(@RequestBody @Validated({ValidateGroup.Add.class}) QuartzConfigDTO configDTO) {
        return quartzJobService.addJob(configDTO);

    }

    /**
     * 暂停任务
     *
     * @param configDTO
     * @return
     */
    @RequestMapping("/pauseJob")
    public Object pauseJob(@RequestBody @Validated({ValidateGroup.Update.class}) QuartzConfigDTO configDTO) {
        return quartzJobService.pauseJob(configDTO.getJobName(), configDTO.getGroupName());
    }

    /**
     * 恢复任务
     *
     * @param configDTO
     * @return
     */
    @RequestMapping("/resumeJob")
    public Object resumeJob(@RequestBody @Validated({ValidateGroup.Update.class}) QuartzConfigDTO configDTO) {
        return quartzJobService.resumeJob(configDTO.getJobName(), configDTO.getGroupName());
    }

    /**
     * 立即运行一次定时任务
     *
     * @param configDTO
     * @return
     */
    @RequestMapping("/runOnce")
    public Object runOnce(@RequestBody @Validated({ValidateGroup.Update.class}) QuartzConfigDTO configDTO) {
        return quartzJobService.runOnce(configDTO.getJobName(), configDTO.getGroupName());
    }

    /**
     * 更新任务,立即生效
     *
     * @param configDTO
     * @return
     */
    @RequestMapping("/updateJob")
    public Object updateJob(@RequestBody @Validated({ValidateGroup.Update.class}) QuartzConfigDTO configDTO) {
        return quartzJobService.updateJob(configDTO);
    }

    /**
     * 删除任务，立即生效，删除之后，任务立即消失
     *
     * @param configDTO
     * @return
     */
    @RequestMapping("/deleteJob")
    public Object deleteJob(@RequestBody @Validated({ValidateGroup.Update.class}) QuartzConfigDTO configDTO) {
        return quartzJobService.deleteJob(configDTO.getJobName(), configDTO.getGroupName());
    }

    /**
     * 启动所有任务
     *
     * @return
     */
    @RequestMapping("/startAllJobs")
    public Object startAllJobs() {
        return quartzJobService.startAllJobs();
    }

    /**
     * 暂停所有任务
     *
     * @return
     */
    @RequestMapping("/pauseAllJobs")
    public Object pauseAllJobs() {
        return quartzJobService.pauseAllJobs();
    }

    /**
     * 恢复所有任务
     *
     * @return
     */
    @RequestMapping("/resumeAllJobs")
    public Object resumeAllJobs() {
        return quartzJobService.resumeAllJobs();
    }

    /**
     * 关闭所有任务
     * 需谨慎操作关闭scheduler容器
     * scheduler生命周期结束，无法再 start() 启动scheduler
     *
     * @return
     */
    @RequestMapping("/shutdownAllJobs")
    public Object shutdownAllJobs() {
        return quartzJobService.shutdownAllJobs();
    }

    /**
     * 分页任务
     *
     * @param configDTO
     * @return
     */
    @PostMapping("/page")
    public R page(@RequestBody Map<String, Object> params) {
        return R.ok(quartzJobService.getPage(params));

    }
}
