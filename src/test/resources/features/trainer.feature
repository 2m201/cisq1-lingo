Feature: Training for Lingo
  As a player,
  I want to guess words with the lengths of 5, 6 and 7
  in order to prepare for Lingo.

  Scenario: Start a new game
    When I start a new game
    Then the word to guess has "5" letters
    And I should see the first letter
    And my score is "0"

  Scenario Outline: Start a new round
    Given I am playing a game
    And the round was won
    And the last word had "<previous length>"
    When I start a new round
    Then the word to guess has "<next length>"

    Examples:
      | previous length | next length |
      | 5               | 6           |
      | 6               | 7           |
      | 7               | 5           |

    #Failure path
    Given I am playing a game
    And the round was lost
    Then I cannot start a new round

    #Failure path
    Given I am playing a game
    And I am already playing a round
    When I start a new round
    Then I can not start a new round

    #Failure path
    Given I start a new round
    When I do not have game
    Then I can not start a new round

  Scenario Outline: Guessing a word
    Given that the round is still ongoing
    When I take a "<guess>" for the "<word>"
    Then I will receive "<feedback>" based on my input

    Examples:
    | word  | guess  | feedback                                             |
    | BROOD | BEBOP  | CORRECT, ABSENT, PRESENT, PRESENT, ABSENT            |
    | BROOD | BROOD  | CORRECT, CORRECT, CORRECT, CORRECT, CORRECT          |

    #Failure path
    Given that the round is still ongoing
    When the word I fill in has the wrong amount of letters
    Then I will receive feedback that my input was wrong

    #Failure path
    Given that the round is still ongoing
    When I guess a word more than 5 times
    Then I have lost the game

    #Failure path
    Given that the word has been guessed
    When I try to guess the word
    Then I will receive feedback that I can not guess anymore

    #Failure path
    Given that I lost the game
    When I try to guess the word
    Then I will receive feedback that I can not guess anymore
