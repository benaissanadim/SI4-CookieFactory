Feature: choose store to pick order

  Background:
    Given a factory with stores "BestCookies" with address "Antibes"
    And special store "Bibou" with events "BIRTHDAY" and "WEDDING" with address "Monaco"
    And I am a client having an order

  Scenario: choose a store success
    When I want to choose the store "BestCookies"
    Then the store is assigned to the order

  Scenario: choose a store unsuccess
    When I want to choose the store not in the system
    Then exception "store not found in our factory"

  Scenario: choose a store for special order unsuccess
    Given Order of event "BIRTH"
    When I want to choose the store "Bibou" for the special order
    Then exception "store does not contain the event"

  Scenario: choose a store for special order success
    Given Order of event "BIRTHDAY"
    When I want to choose the store "Bibou" for the special order
    Then the store "Bibou" is assigned to the special order





