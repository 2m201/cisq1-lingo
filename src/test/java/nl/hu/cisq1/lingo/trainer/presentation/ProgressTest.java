package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Mark;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProgressTest {
    WordService wordService = mock(WordService.class);

    @Test
    @DisplayName("Creating a progress")
    void creatingProgress() {
        Game game = new Game();
        when(wordService.provideRandomWord(any())).thenReturn("baard");

        game.startNewRound(wordService);
        game.takeGuess("BEREN");
        Progress progress = game.createProgress();

        assertEquals(0, progress.getScore());
        assertEquals(1, progress.getRoundNumber());
        assertEquals(List.of('B', '.', '.', '.', '.'), progress.getHint().getNewHint());
        assertEquals(List.of(Mark.CORRECT, Mark.ABSENT, Mark.PRESENT, Mark.ABSENT, Mark.ABSENT),
                            progress.getFeedback().getMarks());
    }

}