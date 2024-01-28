package com.java2e.martin.extension.quartz.service;

import com.java2e.martin.common.data.mybatis.service.MartinService;
import com.java2e.martin.extension.quartz.entity.QrtzHistory;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 狮少
 * @version 1.0
 * @date 2021-03-18
 * @describtion
 * @since 1.0
 */
@Transactional(rollbackFor = Exception.class)
public interface QrtzHistoryService extends MartinService<QrtzHistory> {

}
