package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.GameState;
import nl.hu.cisq1.lingo.trainer.domain.Hint;

import java.util.Optional;

public class Progress {
    private final Long gameId;
    private final GameState gameState;
    private final int score;
    private final Hint hint;
    private final Feedback feedback;
    private final int roundNumber;

    public Progress(Long id, GameState gameState, int score, Optional<Hint> hint, Optional<Feedback> feedback, int roundNumber) {
        this.gameId = id;
        this.gameState = gameState;
        this.score = score;
        if (hint.isEmpty()) {
            this.hint = null;
        } else this.hint = hint.get();
        if(feedback.isEmpty()) {
            this.feedback = null;
        } else this.feedback = feedback.get();
        this.roundNumber = roundNumber;
    }

    public Long getGameId() {
        return gameId;
    }
    public GameState getGameState() { return gameState; }
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
