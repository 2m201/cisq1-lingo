package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.words.application.WordService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WordService wordService;

    @MockBean
    private SpringGameRepository gameRepository;

    @BeforeEach
    void beforeEach() {
        when(wordService.provideRandomWord(5)).thenReturn("baard");
    }

    @Test
    @DisplayName("Start a new game")
    void startNewGame() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/lingo/start");

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.gameState").value("PLAYING"))
                .andExpect(jsonPath("$.score").value(0))
                .andExpect(jsonPath("$.hint").exists())
                .andExpect(jsonPath("$.roundNumber").value(1))
                .andExpect(jsonPath("$.feedback").isEmpty());
    }

    @Test
    @DisplayName("Starting a round when possible")
    void startingARound() throws Exception {
        Game game = new Game();
        Progress progress = game.createProgress();
        String id = progress.getGameId().toString();

        when(gameRepository.findById(any())).thenReturn(Optional.of(game));

        RequestBuilder request = MockMvcRequestBuilders.post("/lingo/start/round/" + id);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameState").value("PLAYING"))
                .andExpect(jsonPath("$.score").value(0))
                .andExpect(jsonPath("$.hint").exists())
                .andExpect(jsonPath("$.roundNumber").value(1))
                .andExpect(jsonPath("$.feedback").isEmpty());
    }

    @Test
    @DisplayName("Taking a valid guess")
    void takingGuess() throws Exception {
        Game game = new Game();
        game.startNewRound(wordService);
        Progress progress = game.createProgress();
        String id = progress.getGameId().toString();
        String attempt = "brand";

        when(gameRepository.findById(any())).thenReturn(Optional.of(game));

        RequestBuilder request = MockMvcRequestBuilders.post("/lingo/guess/" + id + "/" + attempt);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameState").value("PLAYING"))
                .andExpect(jsonPath("$.score").value(0))
                .andExpect(jsonPath("$.hint").exists()) //todo way to check the actual hint?
                .andExpect(jsonPath("$.roundNumber").value(1))
                .andExpect(jsonPath("$.feedback").exists());
    }

    @Test
    @DisplayName("Getting a game")
    void gettingGame() throws Exception {
        Game game = new Game();
        Progress progress = game.createProgress();
        String id = progress.getGameId().toString();

        when(gameRepository.findById(any())).thenReturn(Optional.of(game));

        RequestBuilder request = MockMvcRequestBuilders.get("/lingo/" + id);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameState").value("WAITING_FOR_ROUND"))
                .andExpect(jsonPath("$.score").value(0))
                .andExpect(jsonPath("$.hint").isEmpty())
                .andExpect(jsonPath("$.roundNumber").value(0))
                .andExpect(jsonPath("$.feedback").isEmpty());
    }

    @Test
    @DisplayName("Creating a round when not possible")
    void createRoundWhenImpossible() throws Exception {
        Game game = new Game();
        game.startNewRound(wordService);
        Progress progress = game.createProgress();
        String id = progress.getGameId().toString();

        when(gameRepository.findById(any())).thenReturn(Optional.of(game));

        RequestBuilder request = MockMvcRequestBuilders.post("/lingo/start/round/" + id);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Guessing when round is over")
    void guessingWhenRoundOver() throws Exception {
        Game game = new Game();
        game.startNewRound(wordService);
        game.takeGuess("brand");
        game.takeGuess("brand");
        game.takeGuess("brand");
        game.takeGuess("brand");
        game.takeGuess("brand");

        Progress progress = game.createProgress();
        String id = progress.getGameId().toString();

        when(gameRepository.findById(any())).thenReturn(Optional.of(game));

        RequestBuilder request = MockMvcRequestBuilders.post("/lingo/guess/" + id + "/brand");

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Starting a round when the word of last round is too long")
    void startingRoundWithWrongWordLength() throws Exception {
        Game game = new Game();
        when(wordService.provideRandomWord(6)).thenReturn("regenbogen");

        game.startNewRound(wordService);
        game.takeGuess("baard");
        game.startNewRound(wordService);
        game.takeGuess("regenbogen");

        Progress progress = game.createProgress();
        String id = progress.getGameId().toString();

        when(gameRepository.findById(any())).thenReturn(Optional.of(game));

        RequestBuilder request = MockMvcRequestBuilders.post("/lingo/start/round/" + id);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

}