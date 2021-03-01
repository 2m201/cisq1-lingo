package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidRoundException;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private Word wordToBeGuessed;
    private List<Feedback> feedbackList;
    private Hint beginHint;

    public Round(Word wordToBeGuessed){
        this.wordToBeGuessed = wordToBeGuessed;

        feedbackList = new ArrayList<>();
        this.beginHint = new Hint(wordToBeGuessed);
    }

    public Hint makeGuess(String attempt) {
        if (feedbackList.size() == 5) {
            throw new InvalidRoundException();
        } else if (feedbackList.size() ==0) {
            Feedback feedback = new Feedback(attempt, wordToBeGuessed, beginHint.getNewHint());

            feedbackList.add(feedback);
            return feedback.getHint();
        }
        Feedback lastFeedback = feedbackList.get(feedbackList.size() - 1);

        Feedback feedback = new Feedback(attempt, wordToBeGuessed, lastFeedback.getHint().getNewHint());
        feedbackList.add(feedback);
        return feedback.getHint();

    }

    public boolean isWordGuessed(){
        Feedback lastFeedback = feedbackList.get(feedbackList.size() - 1);

        return lastFeedback.isWordGuessed();
    }

    public Hint getBeginHint() {
        return beginHint;
    }

}
