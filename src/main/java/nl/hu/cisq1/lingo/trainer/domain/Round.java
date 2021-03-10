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

        Feedback feedback = new Feedback(attempt, wordToBeGuessed);
        feedbackList.add(feedback);

        return feedback;
    }

    public Hint getHint(){ return new Hint(wordToBeGuessed, feedbackList); }

    public void checkIfPlayerCanGuess(){
        if (feedbackList.size() == 5) {
            throw new InvalidRoundException();
        }
    }

    public boolean isWordGuessed(){
        Feedback lastFeedback = feedbackList.get(feedbackList.size() - 1);

        return lastFeedback.isWordGuessed();
    }

    public Hint getBeginHint() {
        return beginHint;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }
}
