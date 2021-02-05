Feature: Training for Lingo
  As a player,
  I want to guess words with the lengths of 5, 6 and 7
  in order to prepare for Lingo.

  Scenario: Start a new game
    When I start a new game
    Then the word to guess has "5" letters
    And I should see the first letter
    And my score is "0"

  Scenario: Start a new round
    Given that I am playing a game
    And the round was won
    And the last word had "5" letters
    When I start a new round
    Then the word to guess has "6" letters