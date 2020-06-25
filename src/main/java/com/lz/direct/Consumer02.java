package com.lz.direct;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer02 {
    public static void main(String[] args) throws IOException, TimeoutException {
        /**
         * 基本操作:
         * 1.创建连接工厂,设置连接参数
         * 2.创建连接,定义队列
         */
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.65.165");
        factory.setUsername("liuzhi");
        factory.setPassword("123456");
        factory.setVirtualHost("/lz");
        Channel ch = factory.newConnection().createChannel();

        ch.exchangeDeclare("direct", BuiltinExchangeType.DIRECT);
        String queueName = ch.queueDeclare().getQueue();

        // 队列绑定
        ch.queueBind(queueName,"direct","error");
        ch.queueBind(queueName,"direct","info");
        ch.queueBind(queueName,"direct","warning");

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
