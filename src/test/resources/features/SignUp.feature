Feature: SIGN UP => CREATE ACCOUNT
  Background: users in db
    Given given users with emails "hamza@gmail.com" and "nadim@gmail.com"

  Scenario Outline: emails passwords exceptions
    When user enters email <email> and password <password>
    Then exception is <msg>
    Examples:
    |   email           |   password            |     msg                  |
    | "nadim"           |  "iI36Iql4fr#&8L"     | "invalid email!"         |
    | "123"             |  "iI36Iql4fr#&8L"     | "invalid email!"         |
    | "azeer@"          |  "iI36Iql4fr#&8L"     | "invalid email!"         |
    | "azeer@gmail"     |  "iI36Iql4fr#&8L"     | "invalid email!"         |
    | "azeer.gmail.com" |  "iI36Iql4fr#&8L"     | "invalid email!"         |
    | "hz2@gmail.com"   |  "Abvf1"              | "invalid password!"      |
    | "hz2@gmail.com"   |  "1233"               | "invalid password!"      |
    | "hz2@gmail.com"   |  "Nadimmmm12347"      | "invalid password!"      |
    | "nadim@gmail.com" |  "iI36Iql4fr#&8L"     | "email already used!"    |
    | "hamza@gmail.com" |  "iI36Iql4fr#&8L"     | "email already used!"    |


  Scenario Outline: emails passwords good
    When user enters email <email> and password <password>
    Then a new user is registered
    Examples:
      |   email           |   password            |
      | "nadio@gmail.com" |  "Nadimoo987#L"       |
      | "ham@gmail.com"   |  "iI36Iql4fr#&lK"     |
      | "SI4@gmail.com"   |    "Nadimoo987#L"     |
      | "poly@yahoo.fr"   |  "iI36Iql4fr#&lK"     |