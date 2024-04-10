package com.rabbitmq.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.config.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;

//@Component
public class ConsumerListener {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consume(String msg, Message message, Channel channel) throws IOException {
        System.out.println("队列的消息为：" + msg);
        String correlationId = message.getMessageProperties().getCorrelationId();
        System.out.println("唯一标识：" + correlationId);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
