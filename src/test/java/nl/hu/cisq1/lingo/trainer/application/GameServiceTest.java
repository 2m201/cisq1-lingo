package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.application.exception.NoGameFoundException;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.trainer.presentation.Progress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class GameServiceTest {
    private final WordService wordService = mock(WordService.class);
    private SpringGameRepository gameRepository = mock(SpringGameRepository.class);
    private Game game;

    @BeforeEach
    void beforeEach() {
        when(this.wordService.provideRandomWord(any())).thenReturn("geeuw");
        this.game = new Game();
    }

    @Test
    @DisplayName("Finding a game that does not exist")
    void findingANonExistingGame() {
        when(gameRepository.findById(any())).thenReturn(Optional.empty());

        GameService gameService = new GameService(wordService, gameRepository);

        assertThrows(NoGameFoundException.class, () -> gameService.findGame(12));
    }

    @Test
    @DisplayName("Finding a game")
    void findingGame() {
        when(this.gameRepository.findById(any())).thenReturn(Optional.of(this.game));
        GameService gameService = new GameService(this.wordService, this.gameRepository);

        Progress progressGame = this.game.createProgress();
        Progress progressFoundGame = gameService.findGame(this.game.getId());

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
        SpringGameRepository gameRepository = mock(SpringGameRepository.class);

        GameService gameService = new GameService(this.wordService, gameRepository);
        Progress progress = gameService.startNewGame();

        assertEquals(1, progress.getRoundNumber());
    }

    @Test
    @DisplayName("Saves a game after creating")
    void saveGameAfterCreate(){
        SpringGameRepository gameRepository = mock(SpringGameRepository.class);
        GameService gameService = new GameService(this.wordService, gameRepository);

        gameService.startNewGame();

        verify(gameRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Starting a new round when possible")
    void startNewRound(){
        SpringGameRepository gameRepository = mock(SpringGameRepository.class);
        GameService gameService = new GameService(this.wordService, gameRepository);

        when(gameRepository.findById(any())).thenReturn(Optional.of(this.game));
        Progress progress = this.game.createProgress();

        Progress progress1 = gameService.startNewRound(progress.getGameId());

        assertEquals(1, progress1.getRoundNumber());
    }

    @Test
    @DisplayName("Taking a valid guess")
    void takingGuess(){
        SpringGameRepository gameRepository = mock(SpringGameRepository.class);
        GameService gameService = new GameService(this.wordService, gameRepository);
        Progress progress= this.game.createProgress();

        when(gameRepository.findById(any())).thenReturn(Optional.of(this.game));

        gameService.startNewRound(progress.getGameId());

        Progress progress1 = gameService.takeAGuess(progress.getGameId(), "geeuw");

        assertEquals(25, progress1.getScore());
    }

}