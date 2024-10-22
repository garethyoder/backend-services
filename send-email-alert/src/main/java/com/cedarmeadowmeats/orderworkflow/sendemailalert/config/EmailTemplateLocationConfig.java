package com.cedarmeadowmeats.orderworkflow.sendemailalert.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "send-email-alert.email")
public record EmailTemplateLocationConfig(String orderFormAlertEmail,
                                          String confirmationToClientEmail,
                                          String defaultAlertEmail,
                                          String sender,
                                          String noReplySender,
                                          List<String> adminEmails) {
}
