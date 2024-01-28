package com.java2e.martin.extension.quartz.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.java2e.martin.common.core.api.ApiErrorCode;
import com.java2e.martin.common.core.api.R;
import com.java2e.martin.common.log.annotation.MartinLog;
import com.java2e.martin.extension.quartz.entity.QrtzHistory;
import com.java2e.martin.extension.quartz.service.QrtzHistoryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 狮少
 * @version 1.0
 * @date 2021-03-18
 * @describtion
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("qrtzHistory")
@Api(value = "QrtzHistory 控制器", tags = "")
public class QrtzHistoryController {

    @Autowired
    private QrtzHistoryService qrtzHistoryService;


    /**
     * 添加
     *
     * @param qrtzHistory QrtzHistory
     * @return R
     */
    @MartinLog("添加")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('QRTZ_HISTORY_add')")
    public R save(@Valid @RequestBody QrtzHistory qrtzHistory) {
        return R.ok(qrtzHistoryService.save(qrtzHistory));
    }

    /**
     * 删除
     *
     * @param qrtzHistory QrtzHistory
     * @return R
     */
    @MartinLog("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('QRTZ_HISTORY_del')")
    public R removeById(@Valid @RequestBody QrtzHistory qrtzHistory) {
        return R.ok(qrtzHistoryService.removeById(qrtzHistory.getId()));
    }

    /**
     * 编辑
     *
     * @param qrtzHistory QrtzHistory
     * @return R
     */
    @MartinLog("编辑")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('QRTZ_HISTORY_edit')")
    public R update(@Valid @RequestBody QrtzHistory qrtzHistory) {
        return R.ok(qrtzHistoryService.updateById(qrtzHistory));
    }

    /**
     * 通过ID查询
     *
     * @param qrtzHistory QrtzHistory
     * @return R
     */
    @MartinLog("单个查询")
    @PostMapping("/get")
    @PreAuthorize("hasAuthority('QRTZ_HISTORY_get')")
    public R getById(@RequestBody QrtzHistory qrtzHistory) {
        return R.ok(qrtzHistoryService.getById(qrtzHistory.getId()));
    }

    /**
     * 分页查询
     *
     * @param params 分页以及查询参数
     * @return R
     */
    @MartinLog("分页查询")
    @PostMapping("/page")
    @PreAuthorize("hasAuthority('QRTZ_HISTORY_page')")
    public R<IPage> getPage(@RequestBody Map params) {
        try {
            return R.ok(qrtzHistoryService.getPage(params));
        } catch (IllegalAccessException e) {
            log.error("", e);
            return R.failed(ApiErrorCode.FAIL);
        } catch (InstantiationException e) {
            log.error("", e);
            return R.failed(ApiErrorCode.FAIL);
        }
    }

    @MartinLog("批量删除")
    @PostMapping("/deleteBatch")
    @PreAuthorize("hasAuthority('QRTZ_HISTORY_deleteBatch')")
    public R removeBatch(@RequestBody String ids) {
        List<String> idList = Arrays.stream(ids.split(",")).collect(Collectors.toList());
        if (CollUtil.isEmpty(idList)) {
            return R.failed("id 不能为空");
        }
        return R.ok(qrtzHistoryService.removeByIds(idList));
    }


}

