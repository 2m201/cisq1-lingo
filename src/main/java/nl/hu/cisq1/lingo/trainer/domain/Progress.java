package nl.hu.cisq1.lingo.trainer.domain;

public class Progress {
    private final int score;
    private final Hint hint;
    private final Feedback feedback;
    private final int roundNumber;

    public Progress(int score, Hint hint, Feedback feedback, int roundNumber) {
        this.score = score;
        this.hint = hint;
        this.feedback = feedback;
        this.roundNumber = roundNumber;
    }

    public int getScore() {
        return score;
    }
    public Hint getHint() {
        return hint;
    }
    public Feedback getFeedback() {
        return feedback;
    }
    public int getRoundNumber() {
        return roundNumber;
    }
}
