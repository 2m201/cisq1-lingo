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

        assertEquals(progress.getScore(), 0);
        assertEquals(progress.getRoundNumber(), 1);
        assertEquals(progress.getHint().getNewHint(), List.of('B', '.', '.', '.', '.'));
        assertEquals(progress.getFeedback().getMarks(), List.of(Mark.CORRECT,
                                                                Mark.ABSENT,
                                                                Mark.PRESENT,
                                                                Mark.ABSENT,
                                                                Mark.ABSENT));

    }

}