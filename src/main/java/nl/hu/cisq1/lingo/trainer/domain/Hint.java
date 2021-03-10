package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

public class Hint {
    private List<Character> hint = new ArrayList<>();
    private final Word wordToGuess;

    public Hint(Word wordToGuess, List<Feedback> feedbackList) {
        this.wordToGuess = wordToGuess;

        createHint(feedbackList);
    }

    private void createHint(List<Feedback> feedbackList){
        createFirstHint();

        for (Feedback feedback : feedbackList) {
            int index = 0;
            List<Mark> marks = feedback.getMarks();

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

    private void createFirstHint(){
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
