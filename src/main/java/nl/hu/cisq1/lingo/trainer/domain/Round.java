package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.GuessNotAcceptedException;

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

    public Round(){}
    public Round(Word wordToBeGuessed){
        this.wordToBeGuessed = wordToBeGuessed;
        this.feedbackList = new ArrayList<>();
    }

    public Feedback makeGuess(String attempt) {
        checkIfPlayerCanGuess();

        Feedback feedback = new Feedback(attempt.toUpperCase(), this.wordToBeGuessed);
        this.feedbackList.add(feedback);

        return feedback;
    }

    private void checkIfPlayerCanGuess(){
        if (this.feedbackList.size() == 5) {
            throw new GuessNotAcceptedException("Round has five guesses. Cannot guess anymore");
        }
    }

    public boolean isWordGuessed(){
        if (!this.feedbackList.isEmpty()) {
            Feedback lastFeedback = this.feedbackList.get(this.feedbackList.size() - 1);

            return lastFeedback.isWordGuessed();
        }

        return false;
    }

    public Hint getHint(){ return new Hint(this.wordToBeGuessed, this.feedbackList); }

    public int getFeedbackListSize() {
        return this.feedbackList.size();
    }

    public int getWordLength(){
        return wordToBeGuessed.getSpelling().size();
    }

    public Optional<Feedback> getLastFeedback(){
        if (feedbackList.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(feedbackList.get(feedbackList.size() -1));
    }

    public Word getWordToBeGuessed() {
        return wordToBeGuessed;
    }
}
