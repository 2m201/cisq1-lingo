package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
    static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of("brood", List.of('b', '.', '.', '.', '.'), "brouw", List.of(CORRECT, CORRECT, ABSENT, ABSENT, PRESENT), List.of('b', 'r', '.', '.', '.')),
                Arguments.of("brood", List.of('b', 'r', '.', '.', '.'), "broek", List.of(CORRECT, CORRECT, ABSENT, ABSENT, PRESENT), List.of('b', 'r', '.', '.', '.')),
                Arguments.of("brood", List.of('b', 'r', '.', 'o', '.'), "vloer", List.of(CORRECT, CORRECT, ABSENT, CORRECT, PRESENT), List.of('b', 'r', '.', 'o', '.')),
                Arguments.of("brood", List.of('b', '.', '.', '.', '.'), "bongo", List.of(CORRECT, PRESENT, ABSENT, ABSENT, PRESENT), List.of('b', '.', '.', '.', '.')),
                Arguments.of("brood", List.of('.', '.', '.', '.', '.'), "brouw", List.of(CORRECT, CORRECT, ABSENT, ABSENT, PRESENT), List.of('b', 'r', '.', '.', '.'))
        );
    }

    @Test
    @DisplayName("Word is guessed if all letters are correct")
    void wordIsGuessed(){
        //P: Arrange

        //Q: Act
        String attempt = "PAARD";
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT);

        Feedback feedback = new Feedback(attempt, marks);

        //R: Assert

        Assertions.assertTrue(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("Word is not guessed if not all letters are correct")
    void wordIsNotGuessed(){
        String attempt = "PAARD";
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT);

        Feedback feedback = new Feedback(attempt, marks);

        assertFalse(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("Guess is invalid if all letter is invalid")
    void guessIsInvalid(){
        String attempt = "PAARD";
        List<Mark> marks = List.of(INVALID, INVALID, INVALID, INVALID, INVALID);

        Feedback feedback = new Feedback(attempt, marks);

        assertFalse(feedback.isGuessValid());
    }

    @Test
    @DisplayName("Guess is valid if no letter is invalid")
    void guessIsValid(){
        String attempt = "PAARD";
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT);

        Feedback feedback = new Feedback(attempt, marks);

        assertTrue(feedback.isGuessValid());
    }

    @Test
    @DisplayName("Guess and mark list are not the same length")
    void guessAndMarkListHaveDifferentLengths(){
        assertThrows(InvalidFeedbackException.class,
                () -> new Feedback("woord", List.of(CORRECT))
        );
    }

    @ParameterizedTest
    @MethodSource("provideHintExamples")
    @DisplayName("Feedback gives hint based off of the guess and mark list")
    void feedbackGivesHint(String wordToGuess, List<Character> previousHint, String attempt, List<Mark> marks, List<Character> expectedHint){
        Feedback feedback = new Feedback(attempt, marks);

        List<Character> definiteHint = feedback.giveHint(wordToGuess, previousHint);

        assertEquals(expectedHint, definiteHint);
    }

}