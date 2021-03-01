package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidHintException;

import java.util.ArrayList;
import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.INVALID;

public class Hint {
    private List<Character> newHint = new ArrayList<>();
    private final Word wordToGuess;

    public Hint(Word wordToGuess, List<Mark> marks, List<Character> previousHint) {
        if (previousHint.size() != wordToGuess.getSpelling().size()) {
            throw new InvalidHintException();
        }
        this.wordToGuess = wordToGuess;

        createHint(marks, previousHint);
    }

    public Hint(Word wordToGuess) {
        this.wordToGuess = wordToGuess;

        createFirstHint();
    }

    public void createHint(List<Mark> marks, List<Character> previousHint){
        int index = 0;

        if (marks.stream().allMatch(mark -> mark == INVALID)) {
            this.newHint = previousHint;
        } else {
            for (Character letter : previousHint) {
                if (letter == '.') {
                    if (marks.get(index) == Mark.CORRECT) {
                        newHint.add(wordToGuess.getSpelling().get(index));
                    } else {
                        newHint.add('.');
                    }
                } else {
                    newHint.add(letter);
                }
                index += 1;
            }
        }
    }

    public void createFirstHint(){
        for (Character character : wordToGuess.getSpelling()) {
            if (wordToGuess.getSpelling().indexOf(character) == 0) {
                newHint.add(character);
            } else {
                newHint.add('.');
            }
        }
    }

    public List<Character> getNewHint() {
        return newHint;
    }
}
