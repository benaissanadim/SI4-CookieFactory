Feature: Can Order
  Background:
    Given a client "Nadim"
    And two orders o1 and o2

  Scenario: inability to order
    When the client canceled o1 at 21:15 and o2 at 21:16
    Then he cannot order o3 at 21:17

  Scenario:  eligibility to order
    When o1 cancelled at 8h30
    And o2 cancelled at 8h45
    Then he can order at 8h50

  Scenario: eligibility to order
    When o1 cancelled at 8h30
    Then he is able to order again at 8h32
