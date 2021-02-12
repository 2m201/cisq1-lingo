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
    Scenario: Cannot start a round if eliminated
    Given I am playing a game
    And I have been eliminated
    Then I cannot start a new round

    #Failure path
    Scenario: Cannot start a round if still guessing
    Given I am playing a game
    And I am still guessing a word
    Then I can not start a new round

    #Failure path
    Scenario: Cannot start a round if no game present
    Given I start a new round
    When I do not have game
    Then I can not start a new round

    #Failure path
    Scenario: Cannot guess word if round not started
    Given I am playing a game
    And the round was won
    Then I can not guess the word

  Scenario Outline: Guessing a word
    Given that the round is still ongoing
    When I take a "<guess>" for the "<word>"
    Then I will receive "<feedback>" based on my input

    Examples:
    | word  | guess  | feedback                                             |
    | BROOD | BEBOP  | CORRECT, ABSENT, PRESENT, PRESENT, ABSENT            |
    | BROOD | BROOD  | CORRECT, CORRECT, CORRECT, CORRECT, CORRECT          |

    #Failure path
    Scenario: Cannot take attempt when its length is not the same as the to be guessed word
    Given that the round is still ongoing
    When the word I fill in has the wrong amount of letters
    Then I will receive feedback that my input was wrong

    #Failure path
    Scenario: Cannot guess a word that has already been guessed
    Given that the word has been guessed
    When I try to guess the word
    Then I will receive feedback that I can not guess anymore

    #Failure path
    Scenario: Cannot guess a word when I have been eliminated
    Given I have been eliminated
    When I try to guess the word
    Then I will receive feedback that I can not guess anymore

  Scenario: Cannot guess a word more than 5 times
    Given that the round is still ongoing
    When I guess a word more than 5 times
    Then I have lost the game

  Scenario Outline: Score increases based on number of attempts
    Given I am playing a game
    And the score is "<current score>"
    And the word to guess is "school"
    When I guess "school" in "<attempts>" attempts
    Then the score is "<new score>"

    Examples:
      | current score | attempts | new score |
      | 0             | 1        | 25        |
      | 5             | 1        | 30        |
      | 0             | 2        | 20        |
      | 5             | 2        | 25        |
      | 0             | 3        | 15        |
      | 5             | 3        | 20        |
      | 0             | 4        | 10        |
      | 5             | 4        | 15        |
      | 0             | 5        | 5         |
      | 5             | 5        | 10        |
