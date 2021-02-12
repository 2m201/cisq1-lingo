package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {

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

        assertTrue(feedback.isGuessInvalid());
    }

    @Test
    @DisplayName("Guess is valid if no letter is invalid")
    void guessIsValid(){
        String attempt = "PAARD";
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT);

        Feedback feedback = new Feedback(attempt, marks);

        assertTrue(feedback.isGuessValid());
    }

}