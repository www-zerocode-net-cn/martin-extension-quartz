package com.java2e.martin.extension.quartz.listen;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.listeners.TriggerListenerSupport;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Date;

/**
 * @author 狮少
 * @version 1.0
 * @date 2021/3/12
 * @describtion 任务触发监听器
 * @since 1.0
 */
@Component
@Slf4j
@Data
public class MartinTriggerListener extends TriggerListenerSupport {
    private String triggerFiredMessage = "触发器 {1}.{0} 激活任务 {6}.{5} at: {4, date, yyyy/MM/dd HH:mm:ss }";
    private String triggerMisfiredMessage = "触发器 {1}.{0} 错过任务 {6}.{5}  时间点: {4, date, yyyy/MM/dd HH:mm:ss },  下次执行时间: {3, date, yyyy/MM/dd HH:mm:ss }";
    private String triggerCompleteMessage = "触发器 {1}.{0} 完成任务 {6}.{5} 时间点: {4, date, yyyy/MM/dd HH:mm:ss } ，执行结果编码: {9}";

    /**
     * Trigger监听器的名称
     *
     * @return
     */
    @Override
    public String getName() {
        return "MartinSimpleTriggerListener";
    }

    /**
     * Trigger被激发 它关联的job即将被运行
     *
     * @param trigger
     * @param context
     */
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        if (log.isInfoEnabled()) {
            Object[] args = new Object[]{trigger.getKey().getName(), trigger.getKey().getGroup(), trigger.getPreviousFireTime(), trigger.getNextFireTime(), new Date(), context.getJobDetail().getKey().getName(), context.getJobDetail().getKey().getGroup(), context.getRefireCount()};
            log.info(MessageFormat.format(this.getTriggerFiredMessage(), args));
        }
    }

    /**
     * Trigger被激发 它关联的job即将被运行, TriggerListener 给了一个选择去否决 Job 的执行,如果返回TRUE 那么任务job会被终止
     *
     * @param trigger
     * @param context
     * @return
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        log.info("MartinSimpleTriggerListener.vetoJobExecution()");
        return false;
    }

    /**
     * 当Trigger错过被激发时执行,比如当前时间有很多触发器都需要执行，但是线程池中的有效线程都在工作，
     * 那么有的触发器就有可能超时，错过这一轮的触发。
     *
     * @param trigger
     */
    @Override
    public void triggerMisfired(Trigger trigger) {
        if (log.isInfoEnabled()) {
            Object[] args = new Object[]{trigger.getKey().getName(), trigger.getKey().getGroup(), trigger.getPreviousFireTime(), trigger.getNextFireTime(), new Date(), trigger.getJobKey().getName(), trigger.getJobKey().getGroup()};
            log.info(MessageFormat.format(this.getTriggerMisfiredMessage(), args));
        }
    }

    /**
     * 任务完成时触发
     *
     * @param trigger
     * @param context
     * @param triggerInstructionCode
     */
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        if (log.isInfoEnabled()) {
            String instrCode = "UNKNOWN";
            if (triggerInstructionCode == Trigger.CompletedExecutionInstruction.DELETE_TRIGGER) {
                instrCode = "DELETE TRIGGER";
            } else if (triggerInstructionCode == Trigger.CompletedExecutionInstruction.NOOP) {
                instrCode = "DO NOTHING";
            } else if (triggerInstructionCode == Trigger.CompletedExecutionInstruction.RE_EXECUTE_JOB) {
                instrCode = "RE-EXECUTE JOB";
            } else if (triggerInstructionCode == Trigger.CompletedExecutionInstruction.SET_ALL_JOB_TRIGGERS_COMPLETE) {
                instrCode = "SET ALL OF JOB'S TRIGGERS COMPLETE";
            } else if (triggerInstructionCode == Trigger.CompletedExecutionInstruction.SET_TRIGGER_COMPLETE) {
                instrCode = "SET THIS TRIGGER COMPLETE";
            }

            Object[] args = new Object[]{trigger.getKey().getName(), trigger.getKey().getGroup(), trigger.getPreviousFireTime(), trigger.getNextFireTime(), new Date(), context.getJobDetail().getKey().getName(), context.getJobDetail().getKey().getGroup(), context.getRefireCount(), triggerInstructionCode.toString(), instrCode};
            log.info(MessageFormat.format(this.getTriggerCompleteMessage(), args));
        }
    }
}
