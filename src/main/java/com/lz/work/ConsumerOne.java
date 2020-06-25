package com.lz.work;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerOne {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.65.165");
        factory.setUsername("liuzhi");
        factory.setPassword("123456");
        factory.setVirtualHost("/lz");
        Channel ch = factory.newConnection().createChannel();

        ch.queueDeclare("work",true,false,false,null);

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String msg = new String(message.getBody());
                System.out.println("1号收到消息 : "+ msg);
                for(int i = 0 ; i < msg.length() ; i++){
                    if (msg.charAt(i) == '.'){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("1号处理完毕");
                ch.basicAck(message.getEnvelope().getDeliveryTag(),false);
            }

        };

        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        };
        ch.basicQos(1);
        ch.basicConsume("work",false,deliverCallback,cancelCallback);


    }
}
