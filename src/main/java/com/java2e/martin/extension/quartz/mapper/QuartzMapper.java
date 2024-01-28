package com.java2e.martin.extension.quartz.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author 狮少
 * @version 1.0
 * @date 2021/3/17
 * @describtion QuartzMapper
 * @since 1.0
 */
public interface QuartzMapper {
    /**
     * 查询定时任务
     *
     * @param condition
     * @param quartzConfigDTO
     * @return
     */
    List<HashMap> getPage(Page page, @Param("map") HashMap map);
}
