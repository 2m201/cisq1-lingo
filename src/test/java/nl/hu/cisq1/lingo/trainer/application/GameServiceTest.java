package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGameException;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.trainer.presentation.Progress;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {
    WordService wordService = mock(WordService.class);

    @Test
    @DisplayName("Finding a game that does not exist")
    void findingANonExistingGame() {
        SpringGameRepository gameRepository = mock(SpringGameRepository.class);
        when(gameRepository.findById(any())).thenReturn(Optional.empty());
        GameService gameService = new GameService(wordService, gameRepository);

        assertThrows(InvalidGameException.class, () -> gameService.findGame(12));
    }

    @Test
    @DisplayName("Finding a game")
    void findingGame() {
        SpringGameRepository gameRepository = mock(SpringGameRepository.class);
        Game game = new Game();

        when(gameRepository.findById(any())).thenReturn(Optional.of(game));
        GameService gameService = new GameService(wordService, gameRepository);

        assertEquals(game.createProgress(), gameService.findGame(game.getId()));
    }

    @Test
    @DisplayName("Creating a game also creates a round")
    void createGameCreatesRound(){
        SpringGameRepository gameRepository = mock(SpringGameRepository.class);
        when(wordService.provideRandomWord(any())).thenReturn("geeuw");

        GameService gameService = new GameService(wordService, gameRepository);
        Progress progress = gameService.startNewGame();

        assertEquals(1, progress.getRoundNumber());
    }

    @Test
    @DisplayName("Saves a game after creating")
    void saveGameAfterCreate(){
        SpringGameRepository gameRepository = mock(SpringGameRepository.class);
        when(wordService.provideRandomWord(any())).thenReturn("geeuw");
        GameService gameService = new GameService(wordService, gameRepository);

        gameService.startNewGame();

        verify(gameRepository, times(1)).save(any());
    }

//    @Test
//    @DisplayName("Starting a new round when possible")
//    void startNewRound(){
//        SpringGameRepository gameRepository = mock(SpringGameRepository.class);
//        when(wordService.provideRandomWord(5)).thenReturn("geeuw");
//
//        Game game = new Game();
//
//        when(gameRepository.findById(any())).thenReturn(Optional.of(game));
//
//        GameService gameService = new GameService(wordService, gameRepository);
//        Progress progress = gameService.startNewGame();
//        System.out.println(progress);
//        Progress progress1 = gameService.takeAGuess(progress.getGameId(), "geeuw");
//        System.out.println(progress1);
////
////        when(wordService.provideRandomWord(6)).thenReturn("banaan");
////        Progress progress1 = gameService.startNewRound(progress.getGameId());
//
////        assertEquals(2, progress1.getRoundNumber());
//    }

//    @Test
//    @DisplayName("Taking a valid guess")
//    void takingGuess(){
//        SpringGameRepository gameRepository = mock(SpringGameRepository.class);
//        when(wordService.provideRandomWord(5)).thenReturn("geeuw");
//        Game game = new Game();
//
//        when(gameRepository.findById(any())).thenReturn(Optional.of(game));
//
//        GameService gameService = new GameService(wordService, gameRepository);
//        Progress progress = gameService.startNewGame();
//        System.out.println(progress);
//        Progress progress1 = gameService.takeAGuess(progress.getGameId(), "geeuw");
//        System.out.println(progress1);
//    }
}