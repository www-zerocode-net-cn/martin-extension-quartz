package com.java2e.martin.extension.quartz.service.impl;

import com.java2e.martin.common.data.mybatis.service.impl.MartinServiceImpl;
import com.java2e.martin.extension.quartz.entity.QrtzHistory;
import com.java2e.martin.extension.quartz.mapper.QrtzHistoryMapper;
import com.java2e.martin.extension.quartz.service.QrtzHistoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 狮少
 * @version 1.0
 * @date 2021-03-18
 * @describtion
 * @since 1.0
 */
@Service
public class QrtzHistoryServiceImpl extends MartinServiceImpl<QrtzHistoryMapper, QrtzHistory> implements QrtzHistoryService {
    @Override
    protected void setEntity() {
        this.clz = QrtzHistory.class;
    }
}
