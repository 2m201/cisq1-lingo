package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.GuessNotValidException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundNotMadeException;
import nl.hu.cisq1.lingo.trainer.presentation.Progress;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.GameState.PLAYING;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameTest {
    private final WordService wordService = mock(WordService.class);
    private Game game;

    @BeforeEach
    void beforeEach(){
        this.game = new Game();

        when(wordService.provideRandomWord(any())).thenReturn("honden");

    }

    static Stream<Arguments> provideScoreExamples(){
        return Stream.of(
                Arguments.of(List.of("HONDEN"), 25),
                Arguments.of(List.of("HAASJE", "HONDEN"), 20),
                Arguments.of(List.of("HEILIG","HAASJE", "HONDEN"), 15),
                Arguments.of(List.of("HIJGEN","HEILIG","HAASJE", "HONDEN"), 10),
                Arguments.of(List.of("HORECA","HIJGEN","HEILIG","HAASJE", "HONDEN"), 5)
        );
    }

    @Test
    @DisplayName("Starting a new game")
    void startingNewGame(){
        Progress progress = game.createProgress();

        assertEquals(0, progress.getScore());
        assertEquals(GameState.WAITING_FOR_ROUND, progress.getGameState());
        assertEquals(progress.getGameId(), game.getId());
        assertNull(game.getLastRound());
    }

    @Test
    @DisplayName("Starting a new round")
    void startingNewRound(){
        game.startNewRound(wordService);
        Progress progress = game.createProgress();

        assertEquals(1, progress.getRoundNumber());
        assertEquals(PLAYING, progress.getGameState());
    }

    @Test
    @DisplayName("Starting a new round when game is over")
    void startingANewRoundWhenGameIsOver(){
        game.startNewRound(wordService);

        game.takeGuess("HELDEN");
        game.takeGuess("HELDEN");
        game.takeGuess("HELDEN");
        game.takeGuess("HELDEN");
        game.takeGuess("HELDEN");

        assertThrows(RoundNotMadeException.class, () -> game.startNewRound(wordService));
    }

    @Test
    @DisplayName("Starting a new round when already playing a round")
    void startingNewRoundWhenAlreadyPlaying(){
        game.startNewRound(wordService);

        assertThrows(RoundNotMadeException.class, () -> game.startNewRound(wordService));
    }

    @Test
    @DisplayName("Game gives exception when guess cannot be made")
    void makingAGuessWhenNotPossible(){
        game.startNewRound(wordService);

        game.takeGuess("HELDEN");
        game.takeGuess("HELDEN");
        game.takeGuess("HELDEN");
        game.takeGuess("HELDEN");
        game.takeGuess("HELDEN");

        assertThrows(GuessNotValidException.class, () -> game.takeGuess("HONDEN"));
    }

    @ParameterizedTest
    @MethodSource("provideScoreExamples")
    @DisplayName("Game calculates score after a word has been guessed")
    void calculatingScore(List<String> attempts, int expectedScore){
        game.startNewRound(wordService);

        for(String attempt : attempts) {
            game.takeGuess(attempt);
        }

        Progress progress = game.createProgress();

        assertEquals(expectedScore, progress.getScore());
        assertEquals(GameState.WAITING_FOR_ROUND, progress.getGameState());
    }

}