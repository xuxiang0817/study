package com.xuxiang.study.annotation;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListener;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * MetaqMessageConcurrentlyHandler
 *
 * @author xuxiang
 * @since 18/8/2
 */
public abstract class MetaqMessageConcurrentlyHandler<Message> extends MetaqMessageHandler<Message> {

    protected abstract ConsumeConcurrentlyStatus onHandle(final List<MessageExt> originMessages,
                                                          final List<Message> messages) throws Exception;

    @Override
    protected final MessageListener messageListener() {
        return new MessageListenerConcurrently() {

            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> originMessages,
                                                            ConsumeConcurrentlyContext context) {
                try {
                    return onHandle(originMessages, extractMessage(originMessages));
                } catch (Throwable e) {
                    return null;
                }
            }
        };
    }
}
