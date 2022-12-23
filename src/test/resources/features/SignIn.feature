Feature: SIGN IN
  Background: users in db
    Given given users with email "hamza@gmail.com" and "nadim@gmail.com"

  Scenario Outline: sign in succeeded
    When User enters email <email> and password <password>
    Then the user is signed up
    When user want to sign in with email <email> and password <password>
    Then user is signed in with email <email> and password <password>
    Examples:
      |   email           |   password            |
      | "nadio@gmail.com" |  "Nadimoo987#L"       |
      | "ham@gmail.com"   |  "iI36Iql4fr#&lK"     |
      | "SI4@gmail.com"   |    "Nadimoo987#L"     |
      | "poly@yahoo.fr"   |  "iI36Iql4fr#&lK"     |

  Scenario Outline: sign in not succeeded
    When user enters the email <email> and password <password>
    Then the user is registered
    When user enters new email "sccsc@gmail.com" and password "cseczczdc7ç_àç."
    Then user is not signed in with email "sccsc@gmail.com" and password "cseczczdc7ç_àç."
    Examples:
      |   email           |   password            |
      | "nadio@gmail.com" |  "Nadimoo987#L"       |
      | "ham@gmail.com"   |  "iI36Iql4fr#&lK"     |
      | "SI4@gmail.com"   |    "Nadimoo987#L"     |
      | "poly@yahoo.fr"   |  "iI36Iql4fr#&lK"     |
