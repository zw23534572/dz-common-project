package com.dazong.common.mq.dao.mapper;

import com.dazong.common.mq.domian.DZConsumerMessage;
import com.dazong.common.mq.domian.DZMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huqichao
 * @date 2017-10-30 15:57
 **/
public interface MQMessageMapper {

    /**
     * 保存发送消息
     * @param message 消息
     */
    void insertMessage(DZMessage message);

    /**
     * 修改发送消息
     * @param message 消息
     */
    void updateMessage(DZMessage message);

    /**
     * 根据发送消息时唯一id查询消息
     * @param eventId 发送消息时唯一id
     * @return 消息
     */
    DZMessage queryMessageByEventId(@Param("eventId") String eventId);

    /**
     * 根据状态查询指定数量消息列表
     * @param status 状态
     * @param size 数量
     * @return 消息列表
     */
    List<DZMessage> queryMessageByStatus(@Param("status") int status, @Param("size") int size);

    /**
     * 保存消费消息
     * @param consumerMessage 消息
     */
    void insertConsumerMessage(DZConsumerMessage consumerMessage);

    /**
     * 修改消费消息
     * @param consumerMessage 消息
     */
    void updateConsumerMessage(DZConsumerMessage consumerMessage);

    /**
     * 根据状态查询消费消息列表
     * @param status 状态
     * @return 消费消息列表
     */
    List<DZConsumerMessage> queryConsumerMessageByStatus(@Param("status") int status);

    /**
     * 根据发送消息时唯一id以及消费消息的订阅者名称查询消息
     * @param eventId 发送消息时唯一id
     * @param name 订阅者名称
     * @return 消息
     */
    DZConsumerMessage queryConsumerMessageByEventId(@Param("eventId") String eventId,
                                                    @Param("name") String name);

    /**
     * 根据消息组、订阅者名称、状态查询消费消息列表
     * @param groupId 消息组
     * @param name 订阅者名称
     * @param status 状态
     * @return 消费消息列表
     */
    List<DZConsumerMessage> queryConsumerMessageByGroupId(@Param("groupId") String groupId,
                                                          @Param("name") String name,
                                                          @Param("status") int status);

    /**
     * 根据消息id修改状态
     * @param id 消息id
     * @param status 状态
     */
    void updateConsumerMessageStatusById(@Param("id") Long id, @Param("status") int status);
}
