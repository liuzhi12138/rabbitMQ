package com.lz.fanout;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer01 {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.65.165");
        factory.setUsername("liuzhi");
        factory.setPassword("123456");
        factory.setVirtualHost("/lz");
        Channel ch = factory.newConnection().createChannel();

        ch.exchangeDeclare("logs", BuiltinExchangeType.FANOUT);

        String queueName = ch.queueDeclare().getQueue();
        ch.queueBind(queueName, "logs", "");
        System.out.println("等待接收数据");
        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String msg = new String(message.getBody());
                System.out.println("收到 : " + msg);
            }
        };

        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        };


        ch.basicConsume(queueName, true, deliverCallback, cancelCallback);


    }
}
