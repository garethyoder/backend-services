package com.cedarmeadowmeats.orderworkflow.sendemailalert;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import java.util.List;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.amazonaws.services.lambda.runtime.tests.annotations.Event;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.SendEmailRequest;
import software.amazon.awssdk.services.sesv2.model.SendEmailResponse;

@ExtendWith(SpringExtension.class)
@FunctionalSpringBootTest
class SendEmailAlertApplicationFunctionCatalogTests {

    @Autowired
    private SendEmailAlertApplication application;

    @MockBean
    private SesV2Client sesV2Client;
    @Captor
    private ArgumentCaptor<SendEmailRequest> sendEmailRequestArgumentCaptor;

    @BeforeEach
    void setUp() {
        SendEmailResponse sendEmailResponse = SendEmailResponse.builder().build();
        doReturn(sendEmailResponse).when(sesV2Client).sendEmail(sendEmailRequestArgumentCaptor.capture());
    }

    @ParameterizedTest
    @Event(value = "dynamodb/order_form_event.json", type = DynamodbEvent.DynamodbStreamRecord.class)
    public void testOrderFormEvent(DynamodbEvent.DynamodbStreamRecord event) {
        assertThat(event).isNotNull();
        Assertions.assertEquals("Success", application.sendEmailAlert().apply(List.of(event)));

        verify(sesV2Client, times(1)).sendEmail(any(SendEmailRequest.class));

        SendEmailRequest alertEmail = sendEmailRequestArgumentCaptor.getAllValues().getFirst();

        // Alert Email Assertions
        MatcherAssert.assertThat(alertEmail.destination().toAddresses(), hasItems("Gareth Yoder <gyoder@cedarmeadowmeats.com>"));
        MatcherAssert.assertThat(alertEmail.destination().toAddresses(), hasItems("Joy Yoder <jyoder@cedarmeadowmeats.com>"));
        Assertions.assertEquals("noReply <noReply@cedarmeadowmeats.com>", alertEmail.fromEmailAddress(), "Verify the \"noReply\" from sender email.");
        Assertions.assertEquals("client@test.com", alertEmail.replyToAddresses().getFirst(), "Verify the reply to is the client email.");
        Assertions.assertEquals("Cedar Meadow Meats Order Form: John Doe", alertEmail.content().simple().subject().data(), "Verify the email subject line.");
        MatcherAssert.assertThat("Alert email must contain client's name.", alertEmail.content().simple().body().toString(), containsString("John Doe"));
        MatcherAssert.assertThat("Alert email must contain client's phone.", alertEmail.content().simple().body().toString(), containsString("123-456-7890"));
        MatcherAssert.assertThat("Alert email must contain client's email.", alertEmail.content().simple().body().toString(), containsString("client@test.com"));
        MatcherAssert.assertThat("Alert email must contain selection.", alertEmail.content().simple().body().toString(), containsString("Selection"));
        MatcherAssert.assertThat("Alert email must contain comments.", alertEmail.content().simple().body().toString(), containsString("This is a test comment"));
    }

    @ParameterizedTest
    @Event(value = "dynamodb/contact_form_event.json", type = DynamodbEvent.DynamodbStreamRecord.class)
    public void testContactFormEvent(DynamodbEvent.DynamodbStreamRecord event) {
        assertThat(event).isNotNull();
        Assertions.assertEquals("Success", application.sendEmailAlert().apply(List.of(event)));

        verify(sesV2Client, times(1)).sendEmail(any(SendEmailRequest.class));

        SendEmailRequest alertEmail = sendEmailRequestArgumentCaptor.getAllValues().getFirst();

        // Alert Email Assertions
        MatcherAssert.assertThat(alertEmail.destination().toAddresses(), hasItems("Gareth Yoder <gyoder@cedarmeadowmeats.com>"));
        MatcherAssert.assertThat(alertEmail.destination().toAddresses(), hasItems("Joy Yoder <jyoder@cedarmeadowmeats.com>"));
        Assertions.assertEquals("noReply <noReply@cedarmeadowmeats.com>", alertEmail.fromEmailAddress(), "Verify the \"noReply\" from sender email.");
        Assertions.assertEquals("client@test.com", alertEmail.replyToAddresses().getFirst(), "Verify the reply to is the client email.");
        Assertions.assertEquals("Cedar Meadow Naturals Contact Form: Jane Doe", alertEmail.content().simple().subject().data(), "Verify the email subject line.");
        MatcherAssert.assertThat("Alert email must contain client's name.", alertEmail.content().simple().body().toString(), containsString("Jane Doe"));
        MatcherAssert.assertThat("Alert email must contain client's phone.", alertEmail.content().simple().body().toString(), containsString("123-456-7890"));
        MatcherAssert.assertThat("Alert email must contain client's email.", alertEmail.content().simple().body().toString(), containsString("client@test.com"));
        MatcherAssert.assertThat("Alert email must not contain selection (another form).", alertEmail.content().simple().body().toString(), not(containsString("selection")));
        MatcherAssert.assertThat("Alert email must contain comments.", alertEmail.content().simple().body().toString(), containsString("This is a test comment"));
    }

}
