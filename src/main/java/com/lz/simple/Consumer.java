package com.lz.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂,设置连接参数
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.65.165");
        factory.setUsername("liuzhi");
        factory.setPassword("123456");
        factory.setVirtualHost("/lz");
        // 建立连接, 获取通道
        Channel ch = factory.newConnection().createChannel();
        // 定义队列
        ch.queueDeclare("helloworld", false, false, false, null);
        // 发送消息
        while (true) {
            System.out.println("输入发送的消息 :");
            String msg = new Scanner(System.in).nextLine();
            if(msg.equals("n") ){
                ch.close();
                break;
            }
            ch.basicPublish("", "helloworld", null, msg.getBytes());
            System.out.println("消息发送成功");
        }

    }
}
