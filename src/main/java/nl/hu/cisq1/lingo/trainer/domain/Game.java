package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGameException;
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
        checkIfNewRoundCanBegin();

        Round newRound = new Round(word);
        rounds.add(newRound);
        this.gameState = PLAYING;
    }

    public void checkIfNewRoundCanBegin(){
        if (this.gameState != WAITING_FOR_ROUND) {
            throw new InvalidRoundException();
        }
    }

//    public Hint takeGuess(String attempt) {
//        checkIfGuessCanBeMade();
//        Round round = rounds.get(rounds.size() - 1);
//
//        if (round.isWordGuessed()) {
//            this.calculateScore(round);
//            this.gameState = WAITING_FOR_ROUND;
//        } else if (round.getFeedbackList().size() == 5 && !round.isWordGuessed()){
//            this.calculateScore(round);
//            this.gameState = ELIMINATED;
//        }
//
//        return round.makeGuess(attempt);
//    }

    public void checkIfGuessCanBeMade(){
        if (this.gameState != PLAYING) {
            throw new InvalidGameException();
        }
    }

    public void calculateScore(Round round){
        this.score += 5 * (round.getFeedbackList().size()) + 5;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getScore() { return score; }

    public List<Round> getRounds() { return rounds; }
}
