package com.java2e.martin.extension.quartz.dto;

import com.java2e.martin.common.core.support.ValidateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Map;

/**
 * @author 狮少
 * @version 1.0
 * @date 2021/3/12
 * @describtion QuartzConfigDTO
 * @since 1.0
 */
@Data
public class QuartzConfigDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 任务名称
     */
    @NotBlank(message = "任务名不能为空", groups = {ValidateGroup.Add.class, ValidateGroup.Update.class})
    private String jobName;

    /**
     * 任务所属组
     */
    @NotBlank(message = "任务所属组不能为空", groups = {ValidateGroup.Add.class, ValidateGroup.Update.class})
    private String groupName;

    /**
     * 任务描述
     */
    @NotBlank(message = "任务描述不能为空", groups = {ValidateGroup.Add.class})
    private String jobDescription;

    /**
     * 任务执行类
     */
    @NotBlank(message = "任务执行类不能为空", groups = {ValidateGroup.Add.class})
    private String jobClass;

    /**
     * 任务调度时间表达式
     */
    @NotBlank(message = "务调度时间表达式不能为空", groups = {ValidateGroup.Add.class})
    private String cronExpression;

    /**
     * 附加参数
     */
    private Map<String, Object> param;

}
