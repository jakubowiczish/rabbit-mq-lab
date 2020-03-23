package zad2topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static zad2topic.Z2Topic_Consumer.TOPIC_EXCHANGE_NAME;

public class Z2Topic_Producer {

    public static void main(String[] argv) throws Exception {
        System.out.println("Z2 PRODUCER");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        while (true) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the routing key: ");
            String routingKey = bufferedReader.readLine();
            System.out.println("Enter message: ");
            String message = bufferedReader.readLine();

            if ("exit".equals(message)) {
                break;
            }

            channel.basicPublish(TOPIC_EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("Sent: " + message);
        }
    }
}
