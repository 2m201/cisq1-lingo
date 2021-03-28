package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.application.exception.NoGameFoundException;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.exception.GuessNotValidException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundNotMadeException;
import nl.hu.cisq1.lingo.trainer.presentation.Progress;
import nl.hu.cisq1.lingo.words.application.WordService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
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

    @BeforeEach
    void beforeEach() {
        when(wordService.provideRandomWord(any())).thenReturn("geeuw");
    }

    @Test
    @DisplayName("Finding a non existing game")
    void findingNonExistingGame() {
        when(gameRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoGameFoundException.class, () -> gameService.findGame(22));
    }

    @Test
    @DisplayName("Finding a non existing game")
    void findingExistingGame() {
        Game game = new Game();
        when(gameRepository.findById(any())).thenReturn(Optional.of(game));

        assertEquals(game.createProgress(), gameService.findGame(game.getId()));
    }

    @Test
    @DisplayName("Creating a game also creates a round")
    void createGameCreatesRound(){
        Progress progress = gameService.startNewGame();

        assertEquals(1, progress.getRoundNumber());
        assertEquals(0, progress.getScore());
    }

    @Test
    @DisplayName("Starting a new round when possible")
    void startNewRound(){
        Game game = new Game();
        when(gameRepository.findById(any())).thenReturn(Optional.of(game));
        Progress progress = game.createProgress();

        Progress progress1 = gameService.startNewRound(progress.getGameId());

        assertEquals(1, progress1.getRoundNumber());
        assertEquals(List.of('G', '.', '.','.','.'), progress1.getHint().getNewHint());
        assertNull(progress1.getFeedback());
    }

    @Test
    @DisplayName("Taking a valid guess")
    void takingGuess(){
        Game game = new Game();
        Progress progress= game.createProgress();
        when(gameRepository.findById(any())).thenReturn(Optional.of(game));

        gameService.startNewRound(progress.getGameId());
        Progress progress1 = gameService.takeAGuess(progress.getGameId(), "geeuw");

        assertEquals(25, progress1.getScore());
    }

    @Test
    @DisplayName("Starting round when not possible")
    void startingRoundWhenImpossible() {
        Game game = new Game();
        Progress progress= game.createProgress();
        when(gameRepository.findById(any())).thenReturn(Optional.of(game));

        gameService.startNewRound(progress.getGameId());
        Exception exception = assertThrows(RoundNotMadeException.class, () -> gameService.startNewRound(progress.getGameId()));

        String expectedMessaege = "This game cannot start a new round.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessaege));
    }

    @Test
    @DisplayName("Taking a guess when round is over")
    void guessingWhenRoundOver() {
        Game game = new Game();
        Progress progress= game.createProgress();
        Long id = progress.getGameId();
        when(gameRepository.findById(any())).thenReturn(Optional.of(game));

        gameService.startNewRound(id);
        gameService.takeAGuess(id, "gooit");
        gameService.takeAGuess(id, "gooit");
        gameService.takeAGuess(id, "gooit");
        gameService.takeAGuess(id, "gooit");
        gameService.takeAGuess(id, "gooit");

        assertThrows(GuessNotValidException.class, () -> gameService.takeAGuess(id, "gooit"));
    }



}