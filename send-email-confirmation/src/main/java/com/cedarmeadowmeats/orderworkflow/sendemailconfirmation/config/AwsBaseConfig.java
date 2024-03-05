package com.cedarmeadowmeats.orderworkflow.sendemailconfirmation.config;

import java.util.function.Predicate;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;

public class AwsBaseConfig {

    @Value("${amazon.aws.access-key:}")
    protected String amazonAwsAccessKey;

    @Value("${amazon.aws.secret-key:}")
    protected String amazonAwsSecretKey;

    static boolean isStaticCredentials(String... strings) {
        return Stream.of(strings).anyMatch(Predicate.not(String::isEmpty));
    }
}
