package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidRoundException;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private final Word wordToBeGuessed;
    private List<Feedback> feedbackList;
    private final Hint beginHint;

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
}
