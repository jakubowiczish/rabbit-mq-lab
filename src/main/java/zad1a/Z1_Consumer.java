package zad1a;

import com.rabbitmq.client.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class Z1_Consumer {

    public static void main(String[] argv) throws Exception {
        System.out.println("Z1 CONSUMER");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String QUEUE_NAME = "queue1";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received: " + message);

                int timeToSleep = Integer.parseInt(message);
                sleep(timeToSleep);
                System.out.println("Sleeped: " + message);
//                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        System.out.println("Waiting for messages...");
        channel.basicConsume(QUEUE_NAME, true, consumer);

//        channel.close();
//        connection.close();
    }

    @SneakyThrows
    private static void sleep(int timeToSleep) {
        Thread.sleep(timeToSleep * 1000);
    }
}
