package com.java2e.martin.extension.quartz.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 狮少
 * @version 1.0
 * @date 2021-03-18
 * @describtion
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("QRTZ_HISTORY")
@ApiModel(value = "QrtzHistory对象", description = "")
public class QrtzHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "调度器名称")
    @TableField("SCHED_NAME")
    private String schedName;

    @ApiModelProperty(value = "触发器名称")
    @TableField("TRIGGER_NAME")
    private String triggerName;

    @ApiModelProperty(value = "触发器分组")
    @TableField("TRIGGER_GROUP")
    private String triggerGroup;

    @ApiModelProperty(value = "任务名称")
    @TableField("JOB_NAME")
    private String jobName;

    @ApiModelProperty(value = "任务分组")
    @TableField("JOB_GROUP")
    private String jobGroup;

    @ApiModelProperty(value = "任务执行状态")
    @TableField("JOB_STATUS")
    private String jobStatus;

    @ApiModelProperty(value = "下次执行时间")
    @TableField("NEXT_FIRE_TIME")
    private Long nextFireTime;

    @ApiModelProperty(value = "上次执行时间")
    @TableField("PREV_FIRE_TIME")
    private Long prevFireTime;

    @ApiModelProperty(value = "任务执行记录日志")
    @TableField("JOB_DATA")
    private String jobData;

    @ApiModelProperty(value = "删除标识（0-正常,1-删除）")
    @TableField("DEL_FLAG")
    private String delFlag;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建人")
    @TableField(value = "CREATOR", fill = FieldFill.INSERT)
    private String creator;

    @ApiModelProperty(value = "修改人")
    @TableField(value = "UPDATER", fill = FieldFill.UPDATE)
    private String updater;


}
