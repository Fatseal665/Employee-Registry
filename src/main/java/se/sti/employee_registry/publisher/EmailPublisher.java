package se.sti.employee_registry.publisher;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import se.sti.employee_registry.config.RabbitConfig;

@Service
public class EmailPublisher {

    private final AmqpTemplate amqpTemplate;

    public EmailPublisher(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendUserCreated(String email, String username) {
        String message = "CREATED;" + email + ";" + username;
        amqpTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, message);
    }

    public void sendUserDeleted(String email, String username) {
        String message = "DELETED;" + email + ";" + username;
        amqpTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, message);
    }
}


