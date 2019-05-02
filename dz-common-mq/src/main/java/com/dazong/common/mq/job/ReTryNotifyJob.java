package com.dazong.common.mq.job;

import com.dazong.common.mq.dao.mapper.MQMessageMapper;
import com.dazong.common.mq.domian.DZConsumerMessage;
import com.dazong.common.mq.domian.DZMessage;
import com.dazong.common.mq.manager.MQNotifyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author huqichao
 * @date 2017-10-31 18:23
 **/
@Component
public class ReTryNotifyJob implements Job {

    private Logger logger = LoggerFactory.getLogger(ReTryNotifyJob.class);

    @Autowired
    private MQMessageMapper messageMapper;

    @Autowired
    private MQNotifyManager notifyManager;

    @Override
    public void execute() {
        List<DZConsumerMessage> messageList = messageMapper.queryConsumerMessageByStatus(DZMessage.STATUS_DOING);
        logger.debug("定时重复通知未处理成功的消息：{}", messageList.size());
        for (DZConsumerMessage message : messageList){
            notifyManager.notifyMessage(message);
        }
    }
}
