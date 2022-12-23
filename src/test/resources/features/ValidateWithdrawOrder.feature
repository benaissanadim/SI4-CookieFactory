Feature: validate an order


  Scenario: validate withdraw successfully
    Given an  order
    And its order with status READY
    When a cashier validate the withdraw of the order
    Then the status of the order is TAKEN

  Scenario: validate withdraw unsuccessfully
    Given another order
    When the status of th order is not READY
    Then a cashier cant validate the withdraw of the order

  Scenario: validate withdraw unsuccessfully
    Given an order o
    And its order with status COOKING
    When  cashier validate the withdraw of the order
    Then the status of the order is STILL COOKING

