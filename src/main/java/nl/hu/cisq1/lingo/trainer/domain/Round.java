package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidRoundException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity(name = "round")
public class Round {
    @Id
    @GeneratedValue
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Word wordToBeGuessed;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Feedback> feedbackList;

    @OneToOne(cascade = CascadeType.ALL)
    private Hint beginHint;

    public Round(){}
    public Round(Word wordToBeGuessed){
        this.wordToBeGuessed = wordToBeGuessed;

        this.feedbackList = new ArrayList<>();
        this.beginHint = new Hint(wordToBeGuessed, this.feedbackList);
    }

    public Feedback makeGuess(String attempt) {
        checkIfPlayerCanGuess();

        Feedback feedback = new Feedback(attempt.toUpperCase(), this.wordToBeGuessed);
        this.feedbackList.add(feedback);

        return feedback;
    }

    private void checkIfPlayerCanGuess(){
        if (this.feedbackList.size() == 5) {
            throw new InvalidRoundException();
        }
    }

    public boolean isWordGuessed(){
        Feedback lastFeedback = this.feedbackList.get(this.feedbackList.size() - 1);

        return lastFeedback.isWordGuessed();
    }

    public Hint getHint(){ return new Hint(this.wordToBeGuessed, this.feedbackList); }

    public Hint getBeginHint() {
        return this.beginHint;
    }

    public List<Feedback> getFeedbackList() {
        return this.feedbackList;
    }

    public int getWordLength(){
        return wordToBeGuessed.getSpelling().size();
    }

    public Optional<Feedback> getLastFeedback(){
        if (feedbackList.size() == 0) {
            return Optional.empty();
        }

        return Optional.of(feedbackList.get(feedbackList.size() -1));
    }
}
