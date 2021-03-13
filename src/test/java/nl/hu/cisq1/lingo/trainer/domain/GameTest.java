package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.GameState.PLAYING;
import static nl.hu.cisq1.lingo.trainer.domain.GameState.WAITING_FOR_ROUND;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private static Word word = new Word("HONDEN");


    static Stream<Arguments> provideScoreExamples(){
        return Stream.of(
                Arguments.of(word, List.of("HONDEN"), 25),
                Arguments.of(word, List.of("HAASJE", "HONDEN"), 20),
                Arguments.of(word, List.of("HEILIG","HAASJE", "HONDEN"), 15),
                Arguments.of(word, List.of("HIJGEN","HEILIG","HAASJE", "HONDEN"), 10),
                Arguments.of(word, List.of("HORECA","HIJGEN","HEILIG","HAASJE", "HONDEN"), 5)
        );
    }

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

        game.startNewRound(word);

        assertEquals(1, game.getRounds().size());
        assertEquals(PLAYING, game.getGameState());
    }

    @Test
    @DisplayName("Starting a new round when game is over")
    void startingANewRoundWhenGameIsOver(){
        Game game = new Game();
        game.startNewRound(word);

        game.takeGuess("HELDEN");
        game.takeGuess("HELDEN");
        game.takeGuess("HELDEN");
        game.takeGuess("HELDEN");
        game.takeGuess("HELDEN");

        assertThrows(InvalidGameException.class, () -> game.startNewRound(word));
    }

    @Test
    @DisplayName("Starting a new round when already playing a round")
    void startingNewRoundWhenAlreadyPlaying(){
        Game game = new Game();
        game.startNewRound(word);
        Word word2 = new Word("zeeman");

        assertThrows(InvalidGameException.class, () -> game.startNewRound(word2));
    }

    @Test
    @DisplayName("Game gives exception when guess cannot be made")
    void makingAGuessWhenNotPossible(){
        Game game = new Game();
        game.startNewRound(word);

        game.takeGuess("HELDEN");
        game.takeGuess("HELDEN");
        game.takeGuess("HELDEN");
        game.takeGuess("HELDEN");
        game.takeGuess("HELDEN");

        assertThrows(InvalidGameException.class, () -> game.takeGuess("HONDEN"));
    }

    @ParameterizedTest
    @MethodSource("provideScoreExamples")
    @DisplayName("Game calculates score after a word has been guessed")
    void calculatingScore(Word wordToGuess, List<String> attempts, int expectedScore){
        Game game = new Game();
        game.startNewRound(wordToGuess);

        for(String attempt : attempts) {
            game.takeGuess(attempt);
        }

        assertEquals(expectedScore, game.getScore());
        assertEquals(WAITING_FOR_ROUND, game.getGameState());
    }

    @Test
    @DisplayName("Game returns progress after every guess")
    void gameReturnsProgress(){
        Game game = new Game();
        game.startNewRound(word);

        Progress progress = game.takeGuess("HELDEN");

        assertEquals(0, progress.getScore());
        assertEquals(1, progress.getRoundNumber());
        assertEquals(List.of('H', '.', '.', 'D', 'E', 'N'), progress.getHint().getNewHint());
        assertEquals(List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT), progress.getFeedback().getMarks());
    }
}