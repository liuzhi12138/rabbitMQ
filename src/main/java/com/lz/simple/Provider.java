package com.lz.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Provider {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂,设置连接信息
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.65.165");
        factory.setUsername("liuzhi");
        factory.setPassword("123456");
        factory.setVirtualHost("/lz");
        // 获取连接,创建通道
        Channel ch = factory.newConnection().createChannel();

        //定义队列
        ch.queueDeclare("helloworld",false,false,false,null);

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                byte[] body = message.getBody();
                String msg = new String(body);
                System.out.println("收到: "+msg);
            }
        };

        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        };

        // 接收数据
        ch.basicConsume("helloworld",true,deliverCallback,cancelCallback);
    }

}
