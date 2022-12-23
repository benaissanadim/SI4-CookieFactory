Feature: choose time to pick the order

  Background:
    Given a store with name "Paul" and address "Nice"
    And timetable : "FRIDAY" from 9:30 to 18:15, "SATURDAY" from 10:00 to 17:45, "SUNDAY" from 9:00 to 12:00
    And a client with order

  Scenario: choose time to pick possible
    When the client choose to pick the order from store
    And choose time on 12|11|2022 at 14:00
    Then the order is set to pick 12|11|2022, 14:00

  Scenario: choose time to pick unavailable day TUEDSAY
    When the client choose to pick the order from store
    And choose 15|11|2022 at 14:00
    Then the order can't be set up and exception is thrown

  Scenario: choose time to pick unavailable time
    When the client choose to pick the order from store
    And choose 12|11|2022 at 19:00
    Then the order can't be set up and exception is thrown