package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidRoundException;

import java.util.ArrayList;
import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.GameState.*;

public class Game {
    private GameState gameState;
    private int score;
    private List<Round> rounds;

    public Game(){
        this.gameState = WAITING_FOR_ROUND;
        this.score = 0;
        this.rounds = new ArrayList<>();
    }

    public void startNewRound(Word word){
        if (this.gameState == WAITING_FOR_ROUND) {
            Round newRound = new Round(word);
            rounds.add(newRound);
            this.gameState = PLAYING;
        } else {
            throw new InvalidRoundException();
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getScore() {
        return score;
    }

    public List<Round> getRounds() {
        return rounds;
    }
}
