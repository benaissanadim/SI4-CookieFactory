Feature: validate start cooking

  Background:
    Given store "Paul"
    And cook
    And "PLACED" order with 2 cookies of 1 "Chocolate" and 1 "MMS"
    And a cook affected to the order working in that store

  Scenario: validate the start of cooking successfully
    Given reserved stock 7 "Chocolate" 3 "MMS" and 3 "Vanille"
    When the cook validate the start of cooking of the order
    Then the status of the order "COOKING"
    And reserved stock is 5 "Chocolate" 1 "MMS" and 3 "Vanille"

  Scenario: validate the start of cooking successfully
    Given another reserved stock 1 "Chocolate" 3 "MMS" and 3 "Vanille"
    And "PLACED" order with 2 cookies of 1 "Chocolate" and 1 "MMS"
    And a cook affected to the order working in that store
    When the cook validate this start of cooking of the order
    Then the status of the order still "PLACED"
    And reserved stock still 1 "Chocolate" 3 "MMS" and 3 "Vanille"