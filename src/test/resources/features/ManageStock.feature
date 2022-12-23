Feature: Manage Stock

  Background:
    Given a factory with a store
    And catalog ingredient
    And an ingredient called "Chocolate"
    And an other ingredient called "Vanille"

  Scenario: By default, no ingredients in stock
    When the manager asks for the number of ingredients in stock
    Then There are 0 Ingredient in the stock

  Scenario: Adding Ingredient with its number
    When the manager adds 5 "Chocolate"
    And the manager asks for the number of ingredients
    Then There are 1 Ingredient in stock
    When the manager asks for the number of "Chocolate"
    Then There are 5 in his number of "Chocolate"
    When the manager adds more 4 "Chocolate"
    And the manager asks again for the number of "Chocolate"
    Then There are now 9 in his number of "Chocolate"

  Scenario: Adding another Ingredient with its number
    Given stock is now with 9 "Chocolate"
    When the manager adds another 3 "Vanille"
    And the manager asks now  for the number of ingredients
    Then There are now 2 Ingredient in stock
    When the manager asks for the number of new ingredient "Vanille"
    Then finally 3 in his number of "Vanille"

  Scenario: Adding ingredient not in catalog
    When the manager adds ingredients 3 "newChoc"
    Then ingredients can't be added to stock

  Scenario: decreasing from stock available
    When stock is now with 9 "Chocolate"
    And the manager takes 5 "Chocolate"
    Then stock has 4 "Chocolate"

  Scenario: decreasing from stock unavailable
    When stock is now with 9 "Chocolate"
    Then the manager cant take 11 "Chocolate"
    And cant take 2 "Vanille"
