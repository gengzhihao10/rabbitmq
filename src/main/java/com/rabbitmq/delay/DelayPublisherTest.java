package com.rabbitmq.delay;

import com.rabbitmq.config.DelayedConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DelayPublisherTest {


    @Autowired
    private RabbitTemplate template;

    @Test
    public void publisher(){
        template.convertAndSend(DelayedConfig.DELAYED_EXCHANGE, "delayed.abc", "delayed message", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDelay(3000);
                return message;
            }
        });
    }
}
