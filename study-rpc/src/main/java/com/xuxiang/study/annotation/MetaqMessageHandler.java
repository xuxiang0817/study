package com.xuxiang.study.annotation;

import com.alibaba.rocketmq.client.consumer.listener.MessageListener;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.taobao.metaq.client.MetaPushConsumer;
import com.xuxiang.study.utils.JSONUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * MetaqMessageHandler
 *
 * @author xuxiang
 * @since 18/8/1
 */
@Component
public abstract class MetaqMessageHandler<Message> implements InitializingBean, DisposableBean {

    private String group;
    private String topic;
    private final String subExpression;
    private final int consumeMessageBatchMaxSize;
    private final Class messageType;

    private MetaPushConsumer consumerImp;

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public String getTopic() {
        return topic;
    }

    public String getSubExpression() {
        return subExpression;
    }

    public int getConsumeMessageBatchMaxSize() {
        return consumeMessageBatchMaxSize;
    }

    public Class getMessageType() {
        return messageType;
    }

    public MetaqMessageHandler() {
        Consumer consumer = this.getClass().getAnnotation(Consumer.class);
        group = consumer.group();
        topic = consumer.topic();
        this.subExpression = consumer.subExpression();
        this.messageType = consumer.messageType();
        this.consumeMessageBatchMaxSize = consumer.consumeMessageBatchMaxSize();
    }

    public void afterPropertiesSet() throws Exception {
        consumerImp = new MetaPushConsumer(group);
        consumerImp.subscribe(topic, subExpression);
        consumerImp.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
        MessageListener messageListener = messageListener();
        if (messageListener instanceof MessageListenerConcurrently) {
            consumerImp.registerMessageListener((MessageListenerConcurrently) messageListener);
        } else if (messageListener instanceof MessageListenerOrderly) {
            consumerImp.registerMessageListener((MessageListenerOrderly) messageListener);
        } else {
            throw new IllegalArgumentException("messageListener must be MessageListenerConcurrently or MessageListenerOrderly");
        }
        consumerImp.start();
    }

    public void destroy() throws Exception {
        consumerImp.shutdown();
    }

    protected abstract MessageListener messageListener();

    protected Message deserialize(MessageExt messageExt, Class messageType) {
        return (Message) JSONUtil.fromJsonString(new String(messageExt.getBody(), Charset.forName("UTF-8")), messageType);
    }

    protected final List<Message> extractMessage(List<MessageExt> messageExts) {
        List<Message> messages = new ArrayList<Message>();
        for (MessageExt messageExt : messageExts) {
            messages.add(deserialize(messageExt, messageType));
        }
        return messages;
    }
}
