package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

public class Word {
    private List<Character> spelling;

    public Word(String word) {
        this.spelling = new ArrayList<>();

        for (Character letter : word.toUpperCase().toCharArray()) {
            this.spelling.add(letter);
        }
    }

    public List<Character> getSpelling() {
        return spelling;
    }
}
