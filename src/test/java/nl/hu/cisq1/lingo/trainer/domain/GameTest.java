package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidRoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nl.hu.cisq1.lingo.trainer.domain.GameState.PLAYING;
import static nl.hu.cisq1.lingo.trainer.domain.GameState.WAITING_FOR_ROUND;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    @DisplayName("Starting a new game")
    void startingNewGame(){
        Game game = new Game();

        assertEquals(0, game.getScore());
        assertEquals(WAITING_FOR_ROUND, game.getGameState());
    }

    @Test
    @DisplayName("Starting a new round")
    void startingNewRound(){
        Game game = new Game();
        Word word = new Word("HONDEN");
        game.startNewRound(word);

        assertEquals(1, game.getRounds().size());
        assertEquals(PLAYING, game.getGameState());
    }

    @Test
    @DisplayName("Starting a new round when game is over")
    void startingANewRoundWhenGameIsOver(){
    }

    @Test
    @DisplayName("Starting a new round when already playing a round")
    void startingNewRoundWhenAlreadyPlaying(){
        Game game = new Game();
        Word word = new Word("HONDEN");
        game.startNewRound(word);

        Word word2 = new Word("zeeman");
        assertThrows(InvalidRoundException.class, () -> game.startNewRound(word2));
    }


}