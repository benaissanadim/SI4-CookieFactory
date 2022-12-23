Feature: validate an order

  Background:
    Given a cook affected to the order

  Scenario: validate the end of cooking successfully
    Given order with status COOKING
    When cook validate the end of cooking the order
    Then the status order is READY

  Scenario: validate the end of cooking unsuccessfully
    Given order with status PLACED
    When the cook validate the end of cooking the order
    Then the status order is PLACED
