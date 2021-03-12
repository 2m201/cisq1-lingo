package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidRoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    private static Word word = new Word("PEER");
    private Round round;

    @BeforeEach
    void setUp(){
        this.round = new Round(word);
    }

    @Test
    @DisplayName("Creating a new round")
    void createNewRound(){
        Word word = new Word("PEER");
        Round round = new Round(word);

        assertEquals(round.getBeginHint().getNewHint(), List.of('P', '.', '.', '.'));
        assertEquals(round.getFeedbackList().size(), 0);
    }

    @Test
    @DisplayName("Taking a valid guess")
    void takingAGuess(){
        round.makeGuess("pest");

        assertEquals(round.getHint().getNewHint(), List.of('P', 'E', '.', '.'));
        assertEquals(round.getFeedbackList().size(), 1);
    }

    @Test
    @DisplayName("Getting hint after taking a guess")
    void gettingAHint(){
        round.makeGuess("PEIN");
        round.makeGuess("PAPA");
        round.makeGuess("PEEN");

        assertEquals(List.of('P', 'E', 'E', '.'), round.getHint().getNewHint());
        assertEquals(round.getFeedbackList().size(), 3);
    }

    @Test
    @DisplayName("Taking a guess in a round that already has five guesses")
    void takingMoreThanFiveGuesses(){
        round.makeGuess("PINT");
        round.makeGuess("POND");
        round.makeGuess("PLEE");
        round.makeGuess("PEEN");
        round.makeGuess("PEES");

        assertEquals(5, round.getFeedbackList().size());
        assertThrows(InvalidRoundException.class, () -> round.makeGuess("PEER"));
    }

    @Test
    @DisplayName("Check that the word has been guessed")
    void wordHasBeenGuessed(){
        round.makeGuess("PINT");
        round.makeGuess("POND");
        round.makeGuess("PLEE");
        round.makeGuess("PEER");

        assertTrue(round.isWordGuessed());
        assertEquals(round.getFeedbackList().size(), 4);
    }
}