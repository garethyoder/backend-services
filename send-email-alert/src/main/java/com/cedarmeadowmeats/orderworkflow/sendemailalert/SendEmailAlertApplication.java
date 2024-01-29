package com.cedarmeadowmeats.orderworkflow.sendemailalert;

import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SendEmailAlertApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        SpringApplication.run(SendEmailAlertApplication.class, args);
        LOGGER.info("Application started");
    }

    @Bean
    public Function<List<DynamodbEvent.DynamodbStreamRecord>, String> sendEmailAlert() {
        return value -> {
            LOGGER.info("Printing Event:\n {}", value);
            return "Success";
        };
    }

}
