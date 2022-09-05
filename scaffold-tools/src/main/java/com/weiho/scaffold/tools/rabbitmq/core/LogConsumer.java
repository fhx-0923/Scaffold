package com.weiho.scaffold.tools.rabbitmq.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 消息消费者
 *
 * @author Weiho
 * @date 2022/8/28
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LogConsumer {

    @Async
    @RabbitListener(queues = "${scaffold.rabbitmq.queue-name}", containerFactory = "singleListenerContainer")
    public void consumerMessage(@Payload Object o) {
        try {
            log.info("系统日志记录-消费者-监听消费用户登录成功后的消息-内容：{}", o);
            log.info(" 把消息记录到DB...................................");
        } catch (Exception e) {
            log.error("系统日志记录-消费者-监听消费用户登录成功后的消息-发生异常：{} ", o, e.fillInStackTrace());
        }
    }
}
