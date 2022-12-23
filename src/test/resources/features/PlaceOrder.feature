Feature: place an order

  Background:
    Given order
    And  having 3 cookies "Chocolala" price 2 prep time 5 with dough "Chocolate" flavour "Vanille" topping "WhiteChocolate" and "MilkChocolate"
    And having 2 cookies "cookieso" price 2.5 prep time 15 with dough "Chocolate" topping "MMS"
    And new store "Cookies"
    And cookA "Antoine" available from 10:00 to 12:00 in store on "MONDAY"
    And cookB "Olivier" available from 14:00 to 16:00 in store on "MONDAY"

  Scenario: order is paid and stock available and cook available
    Given available stock of 5 "Chocolate" and 4 "MMS" 3 "Vanille" 3 "WhiteChocolate" 4 "MilkChocolate"
    And client choose to pick at 15:00 14|11|2022
    When the client pays the order with credit card amount 45 and expiration date 01|05|2030
    Then the order status is placed
    And the amount of credit card became 34
    And available stock decreases 0 "Chocolate" 0 "Vanille" 2 "MMS" 0 "WhiteChocolate" 1 "MilkChocolate"
    And the order is assigned to a cook "Olivier"


  Scenario: order is not placed : invalid payment amount credit card
    Given available same stock of 5 "Chocolate" and 2 "MMS" 3 "Vanille" 3 "WhiteChocolate" 4 "MilkChocolate"
    And client choose to pick at 15:00 14|11|2022
    When the client pays the order with credit card amount 10 and expiration date 01|05|2030
    Then exception thrown "not enough amount in your credit card"
    And the order is not assigned to a cook


  Scenario: order is not placed : invalid payment expiration date
    Given available same stock of 5 "Chocolate" and 2 "MMS" 3 "Vanille" 3 "WhiteChocolate" 4 "MilkChocolate"
    And client choose to pick at 15:00 14|11|2022
    When the client pays the order with credit card amount 50 and expiration date 01|05|2020
    Then exception thrown "Your card is Expirated"
    And the order is not assigned to a cook

  Scenario: order is not placed : unavailable stock
    Given another stock of 5 "Chocolate" and 2 "MMS" 2 "WhiteChocolate"
    And client choose to pick at 15:00 14|11|2022
    When the client pays the order with credit card amount 50 and expiration date 01|05|2020
    Then exception thrown "not enough stock"

  Scenario: order is not placed : unavailable cooks
    Given another stock of 5 "Chocolate" and 2 "MMS" 2 "WhiteChocolate"
    And client choose to pick at 18:00 14|11|2022
    When the client pays the order with credit card amount 50 and expiration date 01|05|2020
    Then exception thrown "no cook available"