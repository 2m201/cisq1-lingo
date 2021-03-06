package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "hint")
public class Hint {
    @Id
    @GeneratedValue
    private long id;

    @ElementCollection
    private List<Character> hintList = new ArrayList<>();

    @OneToOne
    private Word wordToGuess;

    public Hint(){}
    public Hint(Word wordToGuess, List<Feedback> feedbackList) {
        if (feedbackList.size() > 5) {
            throw new InvalidFeedbackException("Too many feedback to create hint");
        }
        this.wordToGuess = wordToGuess;
        createHint(feedbackList);
    }

    private void createHint(List<Feedback> feedbackList){
        createFirstHint();

        for (Feedback feedback : feedbackList) {
            int index = 0;
            List<Mark> marks = feedback.getMarks();
                for (Character letter : this.hintList) {
                    if (letter == '.') {
                        if (marks.get(index) == Mark.CORRECT) {
                            this.hintList.set(index, wordToGuess.getSpelling().get(index));
                        } else {
                            this.hintList.set(index, '.');
                        }
                    } else {
                        this.hintList.set(index, letter);
                    }
                    index += 1;
                }
            }
        }

    private void createFirstHint(){
        for (Character character : wordToGuess.getSpelling()) {
            if (wordToGuess.getSpelling().indexOf(character) == 0) {
                this.hintList.add(character);
            } else {
                this.hintList.add('.');
            }
        }
    }

    public List<Character> getNewHint() { return this.hintList; }
}
