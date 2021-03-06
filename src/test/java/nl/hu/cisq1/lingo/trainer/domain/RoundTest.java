package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidRoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    @Test
    @DisplayName("Creating a new round")
    void createNewRound(){
        Word word = new Word("PEER");
        Round round = new Round(word);

        assertEquals(round.getBeginHint().getNewHint(), List.of('P', '.', '.', '.'));
    }

    @Test
    @DisplayName("Taking a valid guess")
    void takingAGuess(){
        Word word = new Word("PEER");
        Round round = new Round(word);

        round.makeGuess("PINT");
    }

    @Test
    @DisplayName("Getting hint after taking a guess")
    void gettingAHint(){
        Word word = new Word("PEER");
        Round round = new Round(word);

        round.makeGuess("PEIN");
        round.makeGuess("PAPA");
        round.makeGuess("PEEN");

        assertEquals(List.of('P', 'E', 'E', '.'), round.getHint().getNewHint());
    }

    @Test
    @DisplayName("Taking a guess in a round that already has five guesses")
    void takingMoreThanFiveGuesses(){
        Word word = new Word("PEER");
        Round round = new Round(word);

        round.makeGuess("PINT");
        round.makeGuess("POND");
        round.makeGuess("PLEE");
        round.makeGuess("PEEN");
        round.makeGuess("PEES");

        assertThrows(InvalidRoundException.class, () -> round.makeGuess("PEER"));
    }

    @Test
    @DisplayName("Check that the word has been guessed")
    void wordHasBeenGuessed(){
        Word word = new Word("PEER");
        Round round = new Round(word);

        round.makeGuess("PINT");
        round.makeGuess("POND");
        round.makeGuess("PLEE");
        round.makeGuess("PEER");

        assertTrue(round.isWordGuessed());
    }
}