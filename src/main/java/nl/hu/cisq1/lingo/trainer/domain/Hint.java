package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidHintException;

import java.util.ArrayList;
import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.INVALID;

public class Hint {
    private List<Character> hint = new ArrayList<>();
    private final Word wordToGuess;

//    public Hint(Word wordToGuess, List<Mark> marks, List<Character> previousHint) {
//        if (previousHint.size() != wordToGuess.getSpelling().size()) {
//            throw new InvalidHintException();
//        }
//        this.wordToGuess = wordToGuess;
//        createHint(marks, previousHint);
//    }

    public Hint(Word wordToGuess) {
        this.wordToGuess = wordToGuess;

        createFirstHint();
    }

    public Hint(Word wordToGuess, List<Feedback> feedbackList, Hint beginHint) {
        this.wordToGuess = wordToGuess;

        if (beginHint == null && feedbackList.size() == 0) {
            createFirstHint();
        } else {
            createHint(feedbackList, beginHint);
        }

    }

    public void createHint(List<Feedback> feedbackList, Hint beginHint){
        for (Feedback feedback : feedbackList) {
            int index = 0;
            List<Mark> marks = feedback.getMarks();
            this.hint = beginHint.getNewHint();

                for (Character letter : this.hint) {
                    if (letter == '.') {
                        if (marks.get(index) == Mark.CORRECT) {
                            this.hint.set(index, wordToGuess.getSpelling().get(index));
                        } else {
                            this.hint.set(index, '.');
                        }
                    } else {
                        this.hint.set(index, letter);
                    }
                    index += 1;
                }
            }
        }


    public void createFirstHint(){
        for (Character character : wordToGuess.getSpelling()) {
            if (wordToGuess.getSpelling().indexOf(character) == 0) {
                this.hint.add(character);
            } else {
                this.hint.add('.');
            }
        }
    }

    public List<Character> getNewHint() { return this.hint; }
}
