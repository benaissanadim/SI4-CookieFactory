Feature: Get Order Price
  Background:
    Given cookie "Chocolala" with dough "Chocolate" price 2 topping "WhiteChocolate" price 2 "MilkChocolate" price 1.5
    And cookie "Choconilla" with dough "Chocolate" price 2, "MMS" topping price 1.5

  Scenario: get price order without taxe
    When client make order of 2 "Chocolala" and 3 "Choconilla"
    And want to get the total price
    Then total price is 21.5

  Scenario: get price order with taxe
    Given store "Cookies" with taxe 20.0%
    When client make order of 2 "Chocolala" and 3 "Choconilla"
    And client choose the store "Cookies" to pick order
    And want to get the total price with taxes
    Then total price with taxes is 25.8

  Scenario: get price order with taxe and loyalty program
    Given store "Cookies" with taxe 20%
    And client having already 32 cookies ordered
    When client make order of 2 "Chocolala" and 3 "Choconilla"
    And client choose the store "Cookies" to pick order
    And want to get the final price
    Then client have discount
    And final price is 23.22
