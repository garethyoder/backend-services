package com.cedarmeadowmeats.orderworkflow.sendemailconfirmation.config;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sesv2.SesV2Client;

@Configuration
public class AwsSesConfig extends AwsBaseConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    // create the dynamoDB client using the credentials and specific region
    private static final Region region = Region.US_EAST_1;

    @Value("${amazon.ses.endpoint:}")
    private String amazonSesEndpoint;

    @Bean
    SesV2Client sesV2Client() {
        SesV2Client sesV2Client;
        if (isStaticCredentials(amazonAwsAccessKey, amazonAwsSecretKey, amazonSesEndpoint)) {
            LOGGER.info("SesV2Client defined by properties");
            AwsCredentialsProvider credentials = StaticCredentialsProvider.create(AwsBasicCredentials.create(amazonAwsAccessKey, amazonAwsSecretKey));
            sesV2Client = SesV2Client.builder().region(region).credentialsProvider(credentials).endpointOverride(URI.create(amazonSesEndpoint)).build();
        } else {
            LOGGER.info("SesV2Client defined by IAM roles");
            sesV2Client = SesV2Client.builder().region(region).build();
        }
        return sesV2Client;
    }

}
