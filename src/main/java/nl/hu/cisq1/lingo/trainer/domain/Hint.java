package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

public class Hint {
    private List<Character> newHint = new ArrayList<>();
    private List<Character> wordToGuess = new ArrayList<>();

    public Hint(String wordToGuess, List<Mark> marks, List<Character> previousHint) {
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
                System.out.println(index);
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
