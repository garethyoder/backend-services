package com.cedarmeadowmeats.orderworkflow.createcustomerinsquare.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cedarmeadowmeats.orderworkflow.createcustomerinsquare.model.Submission;
import com.squareup.square.SquareClient;
import com.squareup.square.api.CustomersApi;
import com.squareup.square.api.DefaultCustomersApi;
import com.squareup.square.exceptions.ApiException;
import com.squareup.square.models.CreateCustomerRequest;
import com.squareup.square.models.CreateCustomerResponse;
import com.squareup.square.models.Customer;
import com.squareup.square.models.SearchCustomersRequest;
import com.squareup.square.models.SearchCustomersResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class SquareServiceTest {

  private Submission submission;

  @InjectMocks
  private SquareService squareService;

  @Mock
  private SquareClient squareClient;

  @Captor
  private ArgumentCaptor<SearchCustomersRequest> searchCustomersRequestArgumentCaptor;

  @Captor
  private ArgumentCaptor<CreateCustomerRequest> createCustomerRequestArgumentCaptor;


  @BeforeEach
  void setUp() {
    submission = new Submission();
    submission.setName("Jane Doe");
    submission.setEmail("client@test.com");
    submission.setPhone("717-368-2610");
    submission.setIdempotencyKey("unique_key");
  }

  @Test
  void findExistingCustomer() throws IOException, ApiException {
    SearchCustomersResponse customersResponse = mock(SearchCustomersResponse.class);

    CustomersApi mockCustomersApi = mock(DefaultCustomersApi.class);
    doReturn(customersResponse).when(mockCustomersApi).searchCustomers(searchCustomersRequestArgumentCaptor.capture());
    when(squareClient.getCustomersApi()).thenReturn(mockCustomersApi);

    squareService.clientSubmission(submission);

    Assertions.assertEquals(submission.getEmail(), searchCustomersRequestArgumentCaptor.getValue().getQuery().toString());
    Assertions.assertEquals(submission.getPhone(), createCustomerRequestArgumentCaptor.getValue().getPhoneNumber());
    Assertions.assertEquals("Jane", createCustomerRequestArgumentCaptor.getValue().getGivenName());
    Assertions.assertEquals("Doe", createCustomerRequestArgumentCaptor.getValue().getFamilyName());
    Assertions.assertEquals(submission.getIdempotencyKey(), createCustomerRequestArgumentCaptor.getValue().getIdempotencyKey());

    // assert Square search query contains email
    assertThat("Search query must contain email.", searchCustomersRequestArgumentCaptor.getValue().getQuery().toString(), containsString(submission.getEmail()));

    verify(mockCustomersApi.searchCustomers(any()), times(1));
    verify(mockCustomersApi.createCustomer(any()), times(0));
  }

  @Test
  void addNewCustomer() throws IOException, ApiException {

    SearchCustomersResponse customersResponse = mock(SearchCustomersResponse.class);

    CustomersApi mockCustomersApi = mock(DefaultCustomersApi.class);

    // Search Customers Mock
    when(customersResponse.getCustomers()).thenReturn(null);
    doReturn(customersResponse).when(mockCustomersApi).searchCustomers(searchCustomersRequestArgumentCaptor.capture());

    // Create Customer Mock
    CreateCustomerResponse createCustomerResponse = mock(CreateCustomerResponse.class);
    doReturn(createCustomerResponse).when(mockCustomersApi).createCustomer(createCustomerRequestArgumentCaptor.capture());
    when(squareClient.getCustomersApi()).thenReturn(mockCustomersApi);

    squareService.clientSubmission(submission);

    Assertions.assertEquals(submission.getEmail(), createCustomerRequestArgumentCaptor.getValue().getEmailAddress());
    Assertions.assertEquals(submission.getPhone(), createCustomerRequestArgumentCaptor.getValue().getPhoneNumber());
    Assertions.assertEquals("Jane", createCustomerRequestArgumentCaptor.getValue().getGivenName());
    Assertions.assertEquals("Doe", createCustomerRequestArgumentCaptor.getValue().getFamilyName());
    Assertions.assertEquals(submission.getIdempotencyKey(), createCustomerRequestArgumentCaptor.getValue().getIdempotencyKey());

    verify(mockCustomersApi.searchCustomers(any()), times(1));
    verify(mockCustomersApi.createCustomer(any()), times(1));
  }

}