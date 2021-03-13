package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProgressTest {

    @Test
    @DisplayName("Creating a progress")
    void creatingProgress(){
        Word word = new Word("BAARD");
        Game game = new Game();
        game.startNewRound(word);
        Progress progress = game.takeGuess("BEREN");

        assertEquals(0, progress.getScore());
        assertEquals(1, progress.getRoundNumber());
        assertEquals(List.of('B', '.', '.', '.', '.'), progress.getHint().getNewHint());
        assertEquals(List.of(Mark.CORRECT, Mark.ABSENT, Mark.PRESENT, Mark.ABSENT, Mark.ABSENT),
                            progress.getFeedback().getMarks());

    }

}