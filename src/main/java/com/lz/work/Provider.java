package com.lz.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Provider {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.65.165");
        factory.setUsername("liuzhi");
        factory.setPassword("123456");
        factory.setVirtualHost("/lz");
        Channel ch = factory.newConnection().createChannel();

        ch.queueDeclare("work", true, false, false, null);

        while (true) {
            System.out.println("请输入消息 :");
            String msg = new Scanner(System.in).nextLine();
            if ("exit".equals(msg))
                break;
            ch.basicPublish("","work", MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getBytes());
            System.out.println("消息以发送");
        }

    }

}
