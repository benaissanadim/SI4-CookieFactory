Feature: create party cookies
  Background:
    Given a cookie "Chocolala" with dough "Chocolate" flavour "Vanille" topping "WhiteChocolate" "MilkChocolate" "MMS" "TOPPED" mix and "CHEWY" cooking
    And with 10$ price

  Scenario Outline: make size bigger and add theme
    When customer makes size <size> and <theme>
    Then a cookie party is created
    And price <price>$ for the party cookie
    And theme new is <t> now
    Examples:

    | size    | theme             |  price    | t            |
    | "L"     | "MUSIC"           |  20       | "MUSIC"      |
    | "XL"    | "PRINCESS"        |  25       | "PRINCESS"     |
    | "XXL"   |"STAR_WARS"        |  30       | "STAR_WARS"    |

  Scenario Outline: add ingredient with dose
    When customer add ingredient <ingredient> of price <p> with dose <x> and size <size>
    Then a cookie party is created
    And price <price>$ for the party cookie
    When remove the ingredient <ingredientRemove>
    Then price <priceNew>$ for the party cookie
    Examples:
      | ingredient    | p     | x   | size |  price    | ingredientRemove|  priceNew |
      | "vanilla"     | 1     |3    | "XL" |  40       | "vanilla"       | 25         |
      | "chococo"     | 1.5   |2    | "XL" |  40       |  "chococo"      | 25         |
      | "choco"       | 2     |2    | "XXL"|  54       |  "choco"        |30          |
      | "chococo"     | 1.5   |2    | "L"  |  32       |  "chococo"      |20         |
      | "vanilla"     | 2     |2    | "L"  |  36       |  "vanilla"      |20          |
      | "ingr"        | 2.5   |3    | "L"  |  50       |  "ingr"         | 20         |




