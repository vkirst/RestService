package my.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean(destroyMethod="stop", initMethod="start")
    public RabbitMQReceiver rabbitMQReceiver() {
        return new RabbitMQReceiver();
    }
}