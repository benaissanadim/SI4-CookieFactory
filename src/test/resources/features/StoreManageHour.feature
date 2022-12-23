Feature: store managing hour
  Background:
    Given I am a store manager

  Scenario: manage store hours for a day
  When I want to manage store scheduler
  And I choose 9:00  as start time as 18:30 as end time for "MONDAY"
  Then I should see the store hours 9:00 to 18:30 for "MONDAY"

  Scenario: manage store hours for a week
    When I want to manage store scheduler
    And I choose 9:00  as start time as 18:00 as end time for a week
    Then I should see the store hours for "MONDAY" "TUESDAY" "WEDNESDAY" "THURSDAY" "FRIDAY" "SATURDAY" "SUNDAY" from 9:00 to 18:00