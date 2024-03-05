package com.cedarmeadowmeats.orderworkflow.sendemailconfirmation.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "send-email-confirmation.email")
public record EmailTemplateLocationConfig(String orderFormAlertEmail,
                                          String confirmationToClientEmail,
                                          String defaultAlertEmail) {
}
