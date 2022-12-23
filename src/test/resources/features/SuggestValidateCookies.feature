Feature: suggest validate cookies in catalog

  Scenario: Validate Cookies
    Given An empty catalog
    And cookies suggested "Chocolalala" price of 3.0
    And "Vanilla" price of 4.0
    When the manager want to validate them in the cookie catalog according to a threshold of 3.5
    Then the manager have the "Chocolalala" cookie added to the catalog
    And the "Vanilla" cookie removed

    Scenario: suggest cookie
      When manager suggest a cookie "Choconilla"
      Then cookie is added to catalog with validation false
