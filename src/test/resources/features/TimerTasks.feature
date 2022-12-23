Feature: Timer Tasks

  Background:
    Given cook affected to the order
    Given cashier

  Scenario: TimerTask ObsoleteOrder created on order ready
    Given order with status the COOKING
    And a cook validate the end of cooking the order
    When status of the order is "READY"
    Then timerTask obsoleteOrder is created

  Scenario: TimerTask ObsoleteOrder deleted on order ready
    Given order with status the COOKING
    And a cook validate the end of cooking the order
    When status of the order is "READY"
    Then timerTask obsoleteOrder is created
    When a cashier validate withdraw of the order
    Then timerTask obsoleteOrder is deleted

  Scenario: TimerTask ObsoleteOrder run after order ready since 2 hours
    Given order with status the COOKING
    And a cook validate the end of cooking the order
    When status of the order is "READY"
    Then timerTask obsoleteOrder is created
    When status of the order is "READY"
    And 120 minutes have passed
    Then status of the order is "OBSOLETE"
    And timerTask obsoleteOrder is deleted

  Scenario: TimerTask ObsoleteOrder run after order ready since 2 hours
    Given order with status the COOKING
    And a cook validate the end of cooking the order
    When status of the order is "READY"
    Then timerTask obsoleteOrder is created
    When status of the order is "READY"
    And 120 minutes have passed
    Then status of the order is "OBSOLETE"
    And timerTask obsoleteOrder is deleted

  Scenario: TimerTask ReminderAfter5Minutes run after pick-up Time passed since 5 minutes
    Given order with status the COOKING
    And a cook validate the end of cooking the order
    When status of the order is "READY"
    Then timerTask reminderAfter5Minutes is created
    When status of the order is "READY"
    And 5 minutes have passed
    And timerTask reminderAfter5Minutes is deleted

  Scenario: TimerTask ReminderAfter1Hour run after pick-up Time passed since 1 hour
    Given order with status the COOKING
    And a cook validate the end of cooking the order
    When status of the order is "READY"
    Then timerTask reminderAfter1Hour is created
    When status of the order is "READY"
    And 60 minutes have passed
    And timerTask reminderAfter1Hour is deleted

  Scenario: TimerTask ReminderSurpriseCart run each 3 hours
    Given TooGoodToGo Surprise Cart Task started
    And timerTask reminderSurpriseCart is created
    When 180 minutes have passed
    Then timerTask reminderSurpriseCart is not deleted
    When 180 minutes have passed
    Then timerTask reminderSurpriseCart is not deleted