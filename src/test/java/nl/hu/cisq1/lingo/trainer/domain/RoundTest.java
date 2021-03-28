package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.GuessNotValidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    private static Word word = new Word("PEREN");
    private Round round;

    @BeforeEach
    void setUp(){
        this.round = new Round(word);
    }

    @Test
    @DisplayName("Creating a new round")
    void createNewRound(){
        assertEquals(List.of('P', '.', '.', '.', '.'), round.getHint().getNewHint());
        assertEquals(0, round.getFeedbackListSize());
        assertEquals(5, round.getWordLength());
        assertFalse(round.isWordGuessed());
    }

    @Test
    @DisplayName("Taking a valid guess")
    void takingAValidGuess(){
        round.makeGuess("peins");

        assertEquals(List.of('P', 'E', '.', '.', '.'), round.getHint().getNewHint());
        assertEquals(1, round.getFeedbackListSize());
        assertFalse(round.isWordGuessed());
    }

    @Test
    @DisplayName("Taking an invalid guess")
    void takingAnInValidGuess(){
        round.makeGuess("perenboom");

        assertEquals(List.of('P', '.', '.', '.', '.'), round.getHint().getNewHint());
        assertEquals(1, round.getFeedbackListSize());
    }

    @Test
    @DisplayName("Getting hint after taking a guess")
    void gettingAHint(){
        round.makeGuess("PEINS");
        round.makeGuess("PAPAS");
        round.makeGuess("PEENS");

        assertEquals(List.of('P', 'E', '.', '.', '.'), round.getHint().getNewHint());
        assertEquals(round.getFeedbackListSize(), 3);
    }

    @Test
    @DisplayName("Taking a guess in a round that has four guesses")
    void takingFiveGuesses(){
        round.makeGuess("PERKT");
        round.makeGuess("PELUW");
        round.makeGuess("PLAAG");
        round.makeGuess("POPJE");
        round.makeGuess("PUTTE");

        assertEquals(5, round.getFeedbackListSize());
        assertFalse(round.isWordGuessed());
        assertEquals(List.of('P','E','R','.','.'), round.getHint().getNewHint());
    }

    @Test
    @DisplayName("Taking a guess in a round that already has five guesses")
    void takingMoreThanFiveGuesses(){
        round.makeGuess("PERKT");
        round.makeGuess("PELUW");
        round.makeGuess("PLAAG");
        round.makeGuess("POPJE");
        round.makeGuess("PUTTE");

        assertEquals(5, round.getFeedbackListSize());
        assertThrows(GuessNotValidException.class, () -> round.makeGuess("PEREN"));
    }

    @Test
    @DisplayName("Check that the word has been guessed")
    void wordHasBeenGuessed(){
        round.makeGuess("PERKT");
        round.makeGuess("PELUW");
        round.makeGuess("PLAAG");
        round.makeGuess("PEREN");

        assertTrue(round.isWordGuessed());
        assertEquals(round.getFeedbackListSize(), 4);
    }

    @Test
    @DisplayName("Check that the word has not been guessed")
    void wordHasNotBeenGuessed(){
        round.makeGuess("PERKT");
        round.makeGuess("PELUW");
        round.makeGuess("PLAAG");

        assertFalse(round.isWordGuessed());
        assertEquals(round.getFeedbackListSize(), 3);
    }
}