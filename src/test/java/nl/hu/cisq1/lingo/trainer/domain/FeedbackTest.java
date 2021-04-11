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
    private Word word = new Word("PAARD");

    static Stream<Arguments> provideMarkExamples(){
        return Stream.of(
                Arguments.of("BREAD", "BAARD", List.of(CORRECT, PRESENT, ABSENT, PRESENT, CORRECT)),
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

        //Q: Act
        Feedback feedback = new Feedback(attempt, word);

        //R: Assert
        Assertions.assertTrue(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("Word is not guessed if not all letters are correct")
    void wordIsNotGuessed(){
        String attempt = "PAARE";

        Feedback feedback = new Feedback(attempt, word);

        assertFalse(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("Guess is invalid if all letter is invalid")
    void guessIsInvalid(){
        String attempt = "REGENBOOM";

        Feedback feedback = new Feedback(attempt, word);

        assertFalse(feedback.isGuessValid());
    }

    @Test
    @DisplayName("Guess is valid if no letter is invalid")
    void guessIsValid(){
        String attempt = "PEREN";

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

//    @Test
//    @DisplayName("Feedback is the same")
//    void seeIfFeedbackIsEquals(){
//        String attempt = "PEREN";
//
//        Feedback feedback = new Feedback(attempt, word);
//
//        assertTrue(feedback.isGuessValid());
//    }

    @Test
    @DisplayName("Hashcode is correct")
    void hashCodeCorrect(){
        Feedback feedback = new Feedback("POPJE", word);
        Feedback feedback1 = new Feedback("POPJE", word);

        assertEquals(feedback.hashCode(), feedback1.hashCode());
    }

    @Test
    @DisplayName("Hashcode is incorrect")
    void hashCodeInCorrect(){
        Feedback feedback = new Feedback("POPJE", word);
        Feedback feedback1 = new Feedback("PAARD", word);

        assertNotEquals(feedback.hashCode(), feedback1.hashCode());
    }

    @Test
    @DisplayName("Equals is correct")
    void equalsCorrect(){
        Feedback feedback = new Feedback("POPJE", word);
        Feedback feedback1 = new Feedback("POPJE", word);

        assertEquals(feedback, feedback1);
    }

    @Test
    @DisplayName("Equals when attempt is different")
    void equalsAttemptInCorrect(){
        Feedback feedback = new Feedback("POPJE", word);
        Feedback feedback1 = new Feedback("PAARD", word);

        assertNotEquals(feedback, feedback1);
    }

    @Test
    @DisplayName("Equals when word is different")
    void equalsWordInCorrect(){
        Word word1 = new Word("HALLO");
        Feedback feedback = new Feedback("PAARD", word);
        Feedback feedback1 = new Feedback("PAARD", word1);

        assertNotEquals(feedback, feedback1);
    }

    @Test
    @DisplayName("Equals method with different object")
    void equalsDifferentObjectInCorrect(){
        Feedback feedback = new Feedback("POPJE", word);
        Round round = new Round(word);

        assertNotEquals(feedback, round);
    }

    @Test
    @DisplayName("ToString method is equal")
    void toStringEqual(){
        Feedback feedback = new Feedback("POPJE", word);
        Feedback feedback1 = new Feedback("POPJE", word);


        assertEquals(feedback.toString(), feedback1.toString());
    }

    @Test
    @DisplayName("ToString method is not equal")
    void toStringNotEqual(){
        Feedback feedback = new Feedback("POPJE", word);
        Feedback feedback1 = new Feedback("PAARD", word);


        assertNotEquals(feedback.toString(), feedback1.toString());
    }


}