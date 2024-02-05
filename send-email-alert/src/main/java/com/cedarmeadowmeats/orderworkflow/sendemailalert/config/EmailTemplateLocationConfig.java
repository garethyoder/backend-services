package com.cedarmeadowmeats.orderworkflow.sendemailalert.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "send-email-alert.email")
public record EmailTemplateLocationConfig(String orderFormAlertEmail,
                                          String confirmationToClientEmail,
                                          String defaultAlertEmail) {
}
