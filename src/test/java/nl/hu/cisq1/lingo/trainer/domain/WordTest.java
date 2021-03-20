package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordTest {

    @Test
    @DisplayName("Creating a word")
    void createWord(){
        Word word = new Word("banaan");

        assertEquals(word.getSpelling(), List.of('B', 'A', 'N', 'A', 'A', 'N'));
    }

}