package com.cedarmeadowmeats.orderworkflow.createcustomerinsquare.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SquareConfigTest {

  private SquareConfig squareConfig;

  @Test
  void testSquareClientSandbox() {
    Assertions.assertNotNull(squareConfig.squareClient());

  }

}