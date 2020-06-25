package com.lz.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Provider {
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

        // 定义交换机
        ch.exchangeDeclare("direct", BuiltinExchangeType.DIRECT);

        // 发送消息
        while (true) {
            System.out.println("输入消息 : ");
            String msg = new Scanner(System.in).nextLine();
            System.out.println("输入路由键 : ");
            String routingKey = new Scanner(System.in).nextLine();
            ch.basicPublish("direct", routingKey, null, msg.getBytes());
            System.out.println("消息已发送 : " + msg + " + " + routingKey);
        }

    }
}
