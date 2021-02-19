package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidHintException;

import java.util.ArrayList;
import java.util.List;

public class Hint {
    private List<Character> newHint = new ArrayList<>();
    private List<Character> wordToGuess = new ArrayList<>();

    public Hint(String wordToGuess, List<Mark> marks, List<Character> previousHint) {
        if (previousHint.size() != wordToGuess.length()) {
            throw new InvalidHintException();
        }


        for (Character letter : wordToGuess.toCharArray()) {
            this.wordToGuess.add(letter);
        }
        createHint(marks, previousHint);
    }

    public void createHint(List<Mark> marks, List<Character> previousHint){
        //ONLY VISUAL -- NO GIVING EXTRA HINT
        // word = BROOD
        // previous HINT : 'B', '.', '.', '.', '.';
        // mark = "CORRECT", "ABSENT", "PRESENT", "CORRECT", "ABSENT"

        int index = 0;
        for(Character letter : previousHint) {
            if (letter == '.') {
                if (marks.get(index) == Mark.CORRECT) {
                    newHint.add(wordToGuess.get(index));
                } else {
                    newHint.add('.');
                }
            } else {
                newHint.add(letter);
            }
            index += 1;
        }
    }

    public List<Character> getNewHint() {
        return newHint;
    }
}
