Feature: create cookie recipe to catalog

  Background:
    Given factory with empty catalog cookie
    And catalog ingredient with dough "Chocolate" flavour "Vanille" topping "WhiteChocolate" "MilkChocolate" "MMS" "buttercup"

  Scenario: No cookie by default
    When the factory wants cookie recipe number
    Then There is 0 cookie

  Scenario: create and add a new recipe with three topping
    When  the factory create cookie "Chocolala" with dough "Chocolate" flavour "Vanille" topping "WhiteChocolate" "MilkChocolate" "MMS" "TOPPED" mix and "CHEWY" cooking
    Then catalog contain 1 cookie

  Scenario: create and add a new recipe with one topping without flavour
    When the factory create a new cookie recipe "Choconilla" with dough "Chocolate", "MMS" topping, "TOPPED" mix and "CHEWY" cooking
    Then catalog contain 1 cookie

  Scenario:  try create a new recipe to the store with mox unavailable
    When the factory create a new recipe named "ChoconillaWhite" with "TOPP" mix
    Then recipe is not created "mix type not found"

  Scenario:  try create a new recipe to the store with cooking unavailable
    When the factory create a new recipe named "ChoconillaWhite" with "COOK" cooking type
    Then recipe is not created "cooking type not found"

  Scenario:  try create a new recipe to the store with cooking unavailable
    When the factory create a new recipe empty
    Then recipe is not created "invalid cookie"

  Scenario:  try create a new recipe to the store with four toppings
    When the factory create a new recipe named "ChoconillaWhite" with "Chocolate" dough, "WhiteChocolate" "MilkChocolate" "MMS" "buttercup" toppings, "TOPPED" mix and "CHEWY" cooking
    Then recipe is not created "toppings must not exceed 3"