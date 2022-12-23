Feature: join loyalty program and apply discount

  Scenario: Client Join Loyalty Program
    Given I am client "Omar"
    When I want to join the loyalty program
    Then I am member of the loyalty program

  Scenario: already client with loyalty program
    Given I am client "Omar" having loyalty program
    When Iwant to  join the loyalty program
    Then exception with "you have already joined loyalty program"
