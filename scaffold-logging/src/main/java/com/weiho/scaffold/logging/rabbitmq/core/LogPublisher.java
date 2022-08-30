package com.weiho.scaffold.logging.rabbitmq.core;

import com.weiho.scaffold.common.config.system.ScaffoldSystemProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 消息生产者
 *
 * @author Weiho
 * @date 2022/8/28
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LogPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final ScaffoldSystemProperties properties;

    @Async
    public void sendLogMessage(Object o) {
        try {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(properties.getRabbitMqProperties().getExchangeName());
            rabbitTemplate.setRoutingKey(properties.getRabbitMqProperties().getRoutingKeyName());
            rabbitTemplate.convertAndSend(o, message -> {
                MessageProperties messageProperties = message.getMessageProperties();
                messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, o.getClass());
                return message;
            });
            log.info("系统日志记录-生产者-发送登录成功后的用户相关信息入队列-内容：{} ", o);
        } catch (Exception e) {
            log.error("系统日志记录-生产者-发送登录成功后的用户相关信息入队列-发生异常：{} ", o, e.fillInStackTrace());
        }
    }
}
