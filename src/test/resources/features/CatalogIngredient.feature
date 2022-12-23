Feature: add ingredient to catalog

  Scenario: catalog empty
    When manager ask for number of ingredients in catalog
    Then 0 found

  Scenario Outline: add ingredient
    When manager add a new ingredient <ingredient> price <price> and name <name>
    Then ingredients added successfully
    When manager ask again for number of ingredients in catalog
    Then 1 found
    Examples:
    | ingredient | price | name     |
    | "TOPPING"  | 2.0   | "CHOCO"  |
    | "FLAVOUR"  | 1.0   | "VANILL" |
    | "DOUGH"    | 2.5    | "DOU"   |
