Feature: explore catalog

  Background:
    Given catalog with cookies validated "Chocolala" "Vanilla" "Best" "Choco" "Chocanilla"

  Scenario: explore all cookies
    When user wants to explore all cookies types
    Then he finds 5 types
