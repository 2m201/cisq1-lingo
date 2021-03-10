package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidRoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.GameState.PLAYING;
import static nl.hu.cisq1.lingo.trainer.domain.GameState.WAITING_FOR_ROUND;
import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static nl.hu.cisq1.lingo.trainer.domain.Mark.PRESENT;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    static Stream<Arguments> provideScoreExamples(){
        return Stream.of(

        );
    }

//    @Test
//    @DisplayName("Starting a new game")
//    void startingNewGame(){
//        Game game = new Game();
//
//        assertEquals(0, game.getScore());
//        assertEquals(WAITING_FOR_ROUND, game.getGameState());
//    }
//
//    @Test
//    @DisplayName("Starting a new round")
//    void startingNewRound(){
//        Game game = new Game();
//        Word word = new Word("HONDEN");
//        game.startNewRound(word);
//
//        assertEquals(1, game.getRounds().size());
//        assertEquals(PLAYING, game.getGameState());
//    }
//
//    @Test
//    @DisplayName("Starting a new round when game is over")
//    void startingANewRoundWhenGameIsOver(){
//    }
//
//    @Test
//    @DisplayName("Starting a new round when already playing a round")
//    void startingNewRoundWhenAlreadyPlaying(){
//        Game game = new Game();
//        Word word = new Word("HONDEN");
//        game.startNewRound(word);
//
//        Word word2 = new Word("zeeman");
//        assertThrows(InvalidRoundException.class, () -> game.startNewRound(word2));
//    }
//
//    @Test
//    @DisplayName("Game gives exception when guess cannot be made")
//    void makingAGuessWhenNotPossible(){
//
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideScoreExamples")
//    @DisplayName("Game calculates score after a word has been guessed")
//    void calculatingScore(String wordToGuess, List<String> attempts, int expectedScore){
//
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideAttemptExample")
//    @DisplayName("Making a valid guess when a round has begun")
//    void makingAGuess(String wordToGuess){
//
//    }


}