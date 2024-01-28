package com.java2e.martin.extension.quartz.listen;

import com.java2e.martin.extension.quartz.entity.QrtzHistory;
import com.java2e.martin.extension.quartz.service.QrtzHistoryService;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.quartz.listeners.JobListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;

/**
 * @author 狮少
 * @version 1.0
 * @date 2021/3/12
 * @describtion 任务执行监听器
 * @since 1.0
 */
@Service
@Slf4j
@Data
public class MartinJobListener extends JobListenerSupport {
    private String jobToBeFiredMessage = "任务 {1}.{0} 被激活 (触发器 {4}.{3})， 时间点：{2, date, yyyy/MM/dd HH:mm:ss }";
    private String jobSuccessMessage = "任务 {1}.{0} 执行成功 ，时间点： {2, date, yyyy/MM/dd HH:mm:ss } ";
    private String jobFailedMessage = "任务 {1}.{0} 执行失败 ，时间点： {2, date, yyyy/MM/dd HH:mm:ss } ";
    private String jobWasVetoedMessage = "任务 {1}.{0} 被拒绝.  它被拒绝的 (触发器 {4}.{3}) 时间点: {2, date, yyyy/MM/dd HH:mm:ss }";
    // 即将执行
    private final static String BEFORE = "0";
    //成功
    private final static String SUCCESS = "1";
    //失败
    private final static String FAIL = "2";
    // 拒绝
    private final static String VETOED = "3";


    @Autowired
    private QrtzHistoryService qrtzHistoryService;

    /**
     * job监听器名称
     *
     * @return
     */
    @Override
    public String getName() {
        return "MartinSimpleJobListener";
    }

    /**
     * 任务被调度前
     *
     * @param context
     */
    @SneakyThrows
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        QrtzHistory qrtzHistory = getQrtzHistory(context, this.getJobToBeFiredMessage());
        qrtzHistory.setJobStatus(BEFORE);
        qrtzHistoryService.save(qrtzHistory);
    }

    /**
     * 任务调度被拒了
     *
     * @param context
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        QrtzHistory qrtzHistory = getQrtzHistory(context, this.getJobWasVetoedMessage());
        qrtzHistory.setJobStatus(VETOED);
        qrtzHistoryService.save(qrtzHistory);
    }


    /**
     * 任务被调度后
     *
     * @param context
     * @param jobException
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        Trigger trigger = context.getTrigger();
        Object[] args = null;
        String errMsg;
        if (jobException != null) {
            errMsg = jobException.getMessage();
            args = new Object[]{context.getJobDetail().getKey().getName(), context.getJobDetail().getKey().getGroup(), new Date(), trigger.getKey().getName(), trigger.getKey().getGroup(), trigger.getPreviousFireTime(), trigger.getNextFireTime(), context.getRefireCount(), errMsg};
            log.warn(MessageFormat.format(this.getJobFailedMessage(), args), jobException);
            QrtzHistory qrtzHistory = getQrtzHistory(context, this.getJobFailedMessage());
            qrtzHistory.setJobStatus(FAIL);
            qrtzHistoryService.save(qrtzHistory);
        } else {
            QrtzHistory qrtzHistory = getQrtzHistory(context, this.getJobSuccessMessage());
            qrtzHistory.setJobStatus(SUCCESS);
            qrtzHistoryService.save(qrtzHistory);
        }
    }

    @SneakyThrows
    private QrtzHistory getQrtzHistory(JobExecutionContext context, String jobCommonMessage) {
        String schedulerName = context.getScheduler().getSchedulerName();
        String jobName = context.getJobDetail().getKey().getName();
        Trigger trigger = context.getTrigger();
        String jobGroup = context.getJobDetail().getKey().getGroup();
        String triggerName = trigger.getKey().getName();
        String triggerGroup = trigger.getKey().getGroup();
        Date previousFireTime = trigger.getPreviousFireTime();
        Date nextFireTime = trigger.getNextFireTime();
        int refireCount = context.getRefireCount();
        Object[] args = new Object[]{jobName, jobGroup, new Date(), triggerName, triggerGroup, previousFireTime, nextFireTime, refireCount};
        String jobMessage = MessageFormat.format(jobCommonMessage, args);
        if (log.isInfoEnabled()) {
            log.info(jobMessage);
        }
        QrtzHistory qrtzHistory = new QrtzHistory();
        qrtzHistory.setSchedName(schedulerName);
        qrtzHistory.setTriggerGroup(triggerGroup);
        qrtzHistory.setTriggerName(triggerName);
        qrtzHistory.setJobGroup(jobGroup);
        qrtzHistory.setJobName(jobName);
        qrtzHistory.setPrevFireTime(previousFireTime.getTime() / 1000);
        qrtzHistory.setNextFireTime(nextFireTime.getTime() / 1000);
        qrtzHistory.setJobData(jobMessage);
        return qrtzHistory;
    }
}
