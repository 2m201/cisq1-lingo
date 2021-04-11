package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.application.exception.NoGameFoundException;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameState;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class GameServiceIntegrationTest {
    @Autowired
    private GameService gameService;

    @MockBean
    private WordService wordService;

    @MockBean
    private SpringGameRepository gameRepository;

    Game game;

    @BeforeEach
    void beforeEach() {
        when(this.wordService.provideRandomWord(any())).thenReturn("geeuw");
        this.game = new Game();
    }

    static Stream<Arguments> provideScoreExamples(){
        return Stream.of(
                Arguments.of(List.of("GEEUW"), 25),
                Arguments.of(List.of("GANZE", "GEEUW"), 20),
                Arguments.of(List.of("GANZE","GANZE", "GEEUW"), 15),
                Arguments.of(List.of("GANZE","GANZE","GANZE", "GEEUW"), 10),
                Arguments.of(List.of("GANZE","GANZE","GANZE","GANZE", "GEEUW"), 5)
        );
    }

    @Test
    @DisplayName("Finding a non existing game")
    void findingNonExistingGame() {
        when(this.gameRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoGameFoundException.class, () -> this.gameService.findGame(22));
    }

    @Test
    @DisplayName("Finding an existing game")
    void findingExistingGame() {
        when(this.gameRepository.findById(any())).thenReturn(Optional.of(this.game));

        Progress progressGame = this.game.createProgress();
        Progress progressFoundGame = this.gameService.findGame(this.game.getId());

        assertEquals(progressGame.getGameId(), progressFoundGame.getGameId());
        assertEquals(progressGame.getGameState(), progressFoundGame.getGameState());
        assertEquals(progressGame.getScore(), progressFoundGame.getScore());
        assertEquals(progressGame.getHint(), progressFoundGame.getHint());
        assertEquals(progressGame.getFeedback(), progressFoundGame.getFeedback());
        assertEquals(progressGame.getRoundNumber(), progressGame.getRoundNumber());
    }

    @Test
    @DisplayName("Creating a game also creates a round")
    void createGameCreatesRound(){
        Progress progress = this.gameService.startNewGame();

        assertEquals(1, progress.getRoundNumber());
    }

    @Test
    @DisplayName("Creating a new game sets score to zero")
    void newGameHasScoreZero(){
        Progress progress = this.gameService.startNewGame();

        assertEquals(0, progress.getScore());
    }

    @Test
    @DisplayName("Starting a new round when possible")
    void startNewRound(){
        when(this.gameRepository.findById(any())).thenReturn(Optional.of(this.game));
        Progress progress = this.game.createProgress();

        Progress progress1 = this.gameService.startNewRound(progress.getGameId());

        assertEquals(1, progress1.getRoundNumber());
    }

    @Test
    @DisplayName("Starting a new round has zero feedback")
    void newRoundHasZeroFeedback(){
        when(this.gameRepository.findById(any())).thenReturn(Optional.of(this.game));
        Progress progress = this.game.createProgress();

        Progress progress1 = this.gameService.startNewRound(progress.getGameId());

        assertNull(progress1.getFeedback());
    }

    @Test
    @DisplayName("Starting a new round gives a hint")
    void newRoundHasHint(){
        when(this.gameRepository.findById(any())).thenReturn(Optional.of(this.game));
        Progress progress = this.game.createProgress();

        Progress progress1 = this.gameService.startNewRound(progress.getGameId());

        assertEquals(List.of('G', '.', '.','.','.'), progress1.getHint().getNewHint());
    }

    @Test
    @DisplayName("Starting round when not possible")
    void startingRoundWhenImpossible() {
        Progress progress = this.game.createProgress();
        Long id = progress.getGameId();
        when(this.gameRepository.findById(any())).thenReturn(Optional.of(game));

        this.gameService.startNewRound(id);

        assertThrows(RoundNotMadeException.class, () -> this.gameService.startNewRound(id));
    }

    @Test
    @DisplayName("Taking a guess when round is over")
    void guessingWhenRoundOver() {
        Progress progress= this.game.createProgress();
        Long id = progress.getGameId();
        when(this.gameRepository.findById(any())).thenReturn(Optional.of(game));

        this.gameService.startNewRound(id);
        this.gameService.takeAGuess(id, "gooit");
        this.gameService.takeAGuess(id, "gooit");
        this.gameService.takeAGuess(id, "gooit");
        this.gameService.takeAGuess(id, "gooit");
        this.gameService.takeAGuess(id, "gooit");

        Exception exception = assertThrows(GuessNotValidException.class, () -> this.gameService.takeAGuess(id, "gooit"));

        String expectedMessage = "This game cannot take a guess.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Guessing a word correctly changes the game state")
    void takingGuess(){
        Progress progress= this.game.createProgress();
        when(this.gameRepository.findById(any())).thenReturn(Optional.of(this.game));

        this.gameService.startNewRound(progress.getGameId());
        Progress progress1 = this.gameService.takeAGuess(progress.getGameId(), "geeuw");

        assertEquals(GameState.WAITING_FOR_ROUND, progress1.getGameState());
    }

    @ParameterizedTest
    @MethodSource("provideScoreExamples")
    @DisplayName("Game calculates score after a word has been guessed")
    void calculatingScore(List<String> attempts, int expectedScore){
        this.game.startNewRound(this.wordService);

        for(String attempt : attempts) {
            this.game.takeGuess(attempt);
        }

        Progress progress = this.game.createProgress();

        assertEquals(expectedScore, progress.getScore());
    }

}