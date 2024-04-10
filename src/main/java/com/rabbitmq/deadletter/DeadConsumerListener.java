package com.rabbitmq.deadletter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.config.DeadLetterConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;

//@Component
public class DeadConsumerListener {




    @RabbitListener(queues = DeadLetterConfig.NORMAL_QUEUE)
    public void consume(String msg, Channel channel, Message message) throws IOException {
        System.out.println("接收到normal队列消息：" + msg);
//        channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);


    }
}
