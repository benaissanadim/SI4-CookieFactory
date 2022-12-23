Feature: Cancel Order
  Background:
    Given a store "Polytech"
    And reserved stock of 5 "Vanille" and 10 "Chacolate"
    And available stock of 4 "Vanille" 9 "Chocolate"
    And factory with tickets order id "1222" and "1555"
    And client with credit card amount 500

  Scenario: Cancel Order not begin to cook
    Given order "PLACED" with ticket id "1222" and price 20
    And having required stock of 3 "Vanille" and 3 "Chacolate"
    When client cancel order
    Then order is Cancelled
    And reserved stock is 2 "Vanille" and 7 "Chacolate"
    And available stock is 7 "Vanille" 12 "Chocolate"
    And factory remove ticket id "1222"
    And client credit card became 520

  Scenario: Cancel Order begin to cook
    Given order "COOKING" with ticket id "1222" and price 20
    And having required stock of 3 "Vanille" and 3 "Chacolate"
    When client cancel order
    Then order is still "COOKING"