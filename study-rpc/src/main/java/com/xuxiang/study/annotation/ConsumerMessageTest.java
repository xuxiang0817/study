package com.xuxiang.study.annotation;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * ConsumerMessageTest
 *
 * @author xuxiang
 * @since 18/8/2
 */
@Consumer(group = "Award-Send-Consumer-Group", topic = "test",
        subExpression = "bbb", messageType = String.class, consumeMessageBatchMaxSize = 2)
public class ConsumerMessageTest extends MetaqMessageConcurrentlyHandler<String>{

    @Override
    protected ConsumeConcurrentlyStatus onHandle(List<MessageExt> originMessages, List<String> strings) throws Exception {
        return null;
    }
}
