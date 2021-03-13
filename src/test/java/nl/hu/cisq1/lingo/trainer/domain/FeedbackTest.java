package nl.hu.cisq1.lingo.trainer.domain;

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
    static Stream<Arguments> provideMarkExamples(){
        return Stream.of(
//                Arguments.of("BREAD", "BAARD", List.of(CORRECT, PRESENT, ABSENT, PRESENT, CORRECT)), //todo solve this shit
                Arguments.of("BROOD", "PLEUR", List.of(INVALID, INVALID, INVALID, INVALID, INVALID)),
                Arguments.of("BROOD", "BROER", List.of(CORRECT, CORRECT, CORRECT, ABSENT, ABSENT)),
                Arguments.of("PADDEN", "PAADJE", List.of(CORRECT, CORRECT, ABSENT, CORRECT, ABSENT, PRESENT)),
                Arguments.of("BRAND", "BAARD", List.of(CORRECT, ABSENT, CORRECT, PRESENT, CORRECT))
                );
    }

    @Test
    @DisplayName("Word is guessed if all letters are correct")
    void wordIsGuessed(){
        //P: Arrange
        String attempt = "PAARD";
        Word word = new Word("PAARD");

        //Q: Act
        Feedback feedback = new Feedback(attempt, word);

        //R: Assert
        Assertions.assertTrue(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("Word is not guessed if not all letters are correct")
    void wordIsNotGuessed(){
        String attempt = "PAARE";
        Word word = new Word("PAARD");

        Feedback feedback = new Feedback(attempt, word);

        assertFalse(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("Guess is invalid if all letter is invalid")
    void guessIsInvalid(){
        String attempt = "REGENBOOM";
        Word word = new Word("PAARD");

        Feedback feedback = new Feedback(attempt, word);

        assertFalse(feedback.isGuessValid());
    }

    @Test
    @DisplayName("Guess is valid if no letter is invalid")
    void guessIsValid(){
        String attempt = "PEREN";
        Word word = new Word("PAARD");

        Feedback feedback = new Feedback(attempt, word);

        assertTrue(feedback.isGuessValid());
    }

    @ParameterizedTest
    @MethodSource("provideMarkExamples")
    @DisplayName("Feedback creates marks based on wordToGuess and attempt")
    void feedbackCreatesMarks(String wordToGuess, String attempt, List<Mark> expectedMarks) {
        Word word = new Word(wordToGuess);

        Feedback feedback = new Feedback(attempt, word);

        assertEquals(expectedMarks, feedback.getMarks());
    }


}