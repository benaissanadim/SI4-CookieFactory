Feature: subscribe too good to go

  Background:
    Given subscribers "nadim.ben.aissaa@gmail.com" "marouane01102001@gmail.com" "osaissi33@gmail.com"
    And store "Polytech" address "930 Rte des Colles, 06410 Biot"

    Scenario: add a subscriber
      When a user add his email "nadimbenaissa@gmail.com"
      Then his email "nadimbenaissa@gmail.com" is added successfully
      And user cant add again his email "nadimbenaissa@gmail.com"

  Scenario: send mail subscribers
    When a new order surprise is created with price 10$ in store "Polytech"
    Then factory emails all 3 subscribers
