Feature: can have discount

  Scenario: a non loyal client
    When a client without a loyalty program wants to get discount
    Then discount not applied

  Scenario: a loyal client less than 30 cookies ordered
    Given a loyal client with 15 cookies ordered
    When client want discount
    Then discount not applied

  Scenario: a loyal client equal 30 cookies ordered
    Given a loyal client with 30 cookies ordered
    When loyal client want discount
    Then discount applied

  Scenario: a loyal client with more than 30 cookies ordered
    Given a loyal client with 35 cookies ordered
    When loyal client want discount
    Then discount applied
    And number cookies became 0 again

