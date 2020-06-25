package com.lz.fanout;

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
        /**
         * 定义交换机
         * 交换机名称 : logs
         * 交换机类型 : FANOUT
         */
        ch.exchangeDeclare("logs", BuiltinExchangeType.FANOUT);

        while (true) {
            System.out.println("输入 :");
            String msg = new Scanner(System.in).nextLine();
            if("exit".equals(msg)){
                break;
            }
            // 向指定的logs交换机发送消息,因为是fanout交换机,所以无需路由键
            ch.basicPublish("logs","",null,msg.getBytes());
        }

        ch.close();

    }
}
