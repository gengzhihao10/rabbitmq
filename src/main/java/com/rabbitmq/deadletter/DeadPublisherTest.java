package com.rabbitmq.deadletter;

import com.rabbitmq.config.DeadLetterConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeadPublisherTest {

    @Autowired
    private RabbitTemplate template;

    @Test
    public void publish(){
        String msg = "dead letter";
        template.convertAndSend(DeadLetterConfig.NORMAL_EXCHANGE,"normal.abc",msg.getBytes());
    }

    @Test
    public void publishExpire(){
        String msg = "dead letter expire";
        template.convertAndSend(DeadLetterConfig.NORMAL_EXCHANGE, "normal.abc", msg, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("5000");
                return message;
            }
        });
    }
}
