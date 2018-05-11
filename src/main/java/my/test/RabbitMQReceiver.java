package my.test;

import java.io.IOException;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RabbitMQReceiver {

    CamelContext context;

    public void start() throws Exception {

        context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                onException(IOException.class)
                        .maximumRedeliveries(-1).redeliveryDelay(2000).maximumRedeliveryDelay(2000);

                restConfiguration().producerComponent("http4")
                        .host("localhost").port(8080);

                from("rabbitmq:localhost:5672/incomingOrders?username=guest&password=guest")
                        .setHeader("name", simple("${body}"))
                        .to("rest:get:greeting?name={name}");

            }
        });

        context.start();

    }

    public void stop() throws Exception {
        if (context != null) {
            context.stop();
        }
    }

}
