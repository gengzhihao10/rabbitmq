package com.rabbitmq.pubsub;

import com.rabbitmq.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
//@RunWith(SpringRunner.class)
public class PublisherTest {

    @Autowired
    public RabbitTemplate template;

    @Test
    public void publish() {
        template.convertAndSend(RabbitMQConfig.EXCHANGE, "big.black.dog", "message");
        System.out.println("消息发送成功");
    }

    @Test
    public void publishWithProps() {
        template.convertAndSend(RabbitMQConfig.EXCHANGE, "big.black.dog", "message with props", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setCorrelationId("123");
                return message;
            }
        });
        System.out.println("消息发送成功");
    }


    @Test
    public void publishWithConfirms() throws IOException {
        template.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack){
                    System.out.println("消息已经送达交换机");
                }
                else {
                    System.out.println("消息没有送达交换机，需要补偿操作。请retry");
                }
            }
        });
        template.convertAndSend(RabbitMQConfig.EXCHANGE, "big.black.dog", "message");
        System.out.println("消息发送成功");
        System.in.read();
    }

    @Test
    public void publishWithReturns() throws IOException {
        template.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                String msg = new String(returned.getMessage().getBody());
                System.out.println("消息：" + msg + "路由失败！做补偿操作！");
            }
        });
        template.convertAndSend(RabbitMQConfig.EXCHANGE, "big.black.dog", "message");
        System.out.println("消息发送成功");
        System.in.read();
    }

    @Test
    public void publishWithBasicProperties() throws IOException {
        template.convertAndSend(RabbitMQConfig.EXCHANGE, "big.black.dog", "message", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                return message;
            }
        });
        System.out.println("消息发送成功");
        System.in.read();
    }
}
