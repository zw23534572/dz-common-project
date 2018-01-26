package com.dazong.common.mq.job;

import com.dazong.common.mq.dao.mapper.MQMessageMapper;
import com.dazong.common.mq.domian.DZMessage;
import com.dazong.common.mq.manager.MQSendManager;
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
public class ReTrySendJob implements Job {

    private static final int SEND_MQ_BATCH = 20;

    private Logger logger = LoggerFactory.getLogger(ReTrySendJob.class);

    @Autowired
    private MQMessageMapper messageMapper;

    @Autowired
    private MQSendManager mqSendManager;

    @Override
    public void execute() {
        List<DZMessage> messageList = messageMapper.queryMessageByStatus(DZMessage.STATUS_DOING, SEND_MQ_BATCH);
        logger.debug("定时重发未发送成功的消息：{}", messageList.size());
        for (DZMessage message : messageList){
            mqSendManager.send(message);
        }
    }
}
