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
//    static Stream<Arguments> provideMarkExamples(){
//        return Stream.of(
//                Arguments.of("BROOD", List.of('B', '.', '.', '.', '.'), "BLEEK", List.of(CORRECT, ABSENT, ABSENT, ABSENT, ABSENT)),
//                Arguments.of("BROOD", List.of('B', '.', '.', '.', '.'), "PLEUR", List.of(INVALID, INVALID, INVALID, INVALID, INVALID)),
//                Arguments.of("BROOD", List.of('B', '.', '.', '.', '.'), "BROER", List.of(CORRECT, CORRECT, CORRECT, ABSENT, ABSENT)),
//                Arguments.of("PADDEN", List.of('P', '.', '.', '.', '.','.'), "PAADJE", List.of(CORRECT, CORRECT, ABSENT, CORRECT, ABSENT, PRESENT))
//        );
//    }
//
//    @Test
//    @DisplayName("Word is guessed if all letters are correct")
//    void wordIsGuessed(){
//        //P: Arrange
//        String attempt = "PAARD";
//        Word word = new Word("PAARD");
//
//
//        //Q: Act
//        Feedback feedback = new Feedback(attempt, word, List.of('P', '.', '.', '.', '.'));
//
//        //R: Assert
//        Assertions.assertTrue(feedback.isWordGuessed());
//    }
//
//    @Test
//    @DisplayName("Word is not guessed if not all letters are correct")
//    void wordIsNotGuessed(){
//        String attempt = "PAARE";
//        Word word = new Word("PAARD");
//
//        Feedback feedback = new Feedback(attempt, word, List.of('P', '.', '.', '.', '.'));
//
//        assertFalse(feedback.isWordGuessed());
//    }
//
//    @Test
//    @DisplayName("Guess is invalid if all letter is invalid")
//    void guessIsInvalid(){
//        String attempt = "REGENBOOM";
//        Word word = new Word("PAARD");
//
//        Feedback feedback = new Feedback(attempt, word, List.of('P', '.', '.', '.', '.'));
//
//        assertFalse(feedback.isGuessValid());
//    }
//
//    @Test
//    @DisplayName("Guess is valid if no letter is invalid")
//    void guessIsValid(){
//        String attempt = "PEREN";
//        Word word = new Word("PAARD");
//
//        Feedback feedback = new Feedback(attempt, word, List.of('P', '.', '.', '.', '.'));
//
//        assertTrue(feedback.isGuessValid());
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideMarkExamples")
//    @DisplayName("Feedback creates marks based on wordToGuess and attempt")
//    void feedbackCreatesMarks(String wordToGuess, List<Character> beginHint, String attempt, List<Mark> expectedMarks) {
//        Word word = new Word(wordToGuess);
//
//        Feedback feedback = new Feedback(attempt, word, beginHint);
//
//        assertEquals(expectedMarks, feedback.getMarks());
//    }


}