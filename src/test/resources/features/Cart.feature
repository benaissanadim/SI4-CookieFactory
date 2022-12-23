Feature: cart

  Background:
    Given I am a client "Nadim"

  Scenario: add cookie to cart
    Given I have an empty cart
    When I choose a "chocolala" cookie with 5 quantity
    Then cart contains 1 item "chocolala" 5 quantity

  Scenario: add a different cookie to a non empty cart
    Given I have a cart that already contains 2 "chocolala" cookies
    When I choose a "vanilla" cookie with 3 quantity
    Then my cart contains 3 "vanilla" & 2 "chocolala"

  Scenario: validate the cart
    When I validate my cart of 10 "chococoo"
    Then my order cantains 10 "chococoo"

  Scenario: update cart
    And I have a cart that already contains 2 "chocolala" cookies
    When I want to add 5 more "chocolala" cookies
    Then the quantity of "chocolala" cookie is set to 7 in the basket

  Scenario: remove from cart
    And I have a cart that already contains 10 "chocolala" cookies
    When I want to remove 3 more "chocolala" cookies
    Then the quantity of "chocolala" cookie is set to 7 in the basket
