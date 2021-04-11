package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.wordstrategy.DefaultWordStrategy;
import nl.hu.cisq1.lingo.trainer.domain.wordstrategy.WordStrategyConverter;
import nl.hu.cisq1.lingo.trainer.domain.wordstrategy.WordStrategyInterface;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundNotMadeException;
import nl.hu.cisq1.lingo.trainer.domain.exception.GuessNotAcceptedException;
import nl.hu.cisq1.lingo.trainer.presentation.Progress;
import nl.hu.cisq1.lingo.words.application.WordService;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static nl.hu.cisq1.lingo.trainer.domain.GameState.*;

@Entity(name = "game")
public class Game {
    @Id
    @GeneratedValue
    private long id;

    @Enumerated(value = EnumType.STRING)
    @Column
    private GameState gameState;

    @Column
    private int score;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Round> rounds;

    @Convert(converter = WordStrategyConverter.class)
    private WordStrategyInterface wordStrategyInterface;

    public Game(){
        this.gameState = WAITING_FOR_ROUND;
        this.score = 0;
        this.rounds = new ArrayList<>();
        this.wordStrategyInterface = new DefaultWordStrategy();
    }

    public void startNewRound(WordService wordService){
        checkIfNewRoundCanBegin();

        int previousWordLength = getPreviousWordLength();
        Word word = this.wordStrategyInterface.generateNextWord(previousWordLength, wordService);

        Round round = new Round(word);
        rounds.add(round);
        this.gameState = PLAYING;
    }

    private void checkIfNewRoundCanBegin(){
        if (this.gameState != WAITING_FOR_ROUND) {
            throw new RoundNotMadeException("This game cannot start a new round.");
        }
    }

    private int getPreviousWordLength(){
        if (this.rounds.isEmpty()) {
            return 7;
        }
        return getLastRound().getWordLength();
    }

    public void takeGuess(String attempt) {
        checkIfGuessCanBeMade();
        Round round = rounds.get(rounds.size() - 1);
        round.makeGuess(attempt);

        if (round.isWordGuessed()) {
            this.calculateScore(round);
            this.gameState = WAITING_FOR_ROUND;
        } else if (round.getFeedbackListSize() == 5 && !round.isWordGuessed()){
            this.calculateScore(round);
            this.gameState = ELIMINATED;
        }
    }

    private void checkIfGuessCanBeMade(){
        if (this.gameState != PLAYING) {
            throw new GuessNotAcceptedException("This game cannot take a guess.");
        }
    }

    public void calculateScore(Round round){
        this.score += 5 * ( 5 - round.getFeedbackListSize()) + 5;
    }

    public Progress createProgress(){
        if (this.gameState == PLAYING) {
            return new Progress(this.id,
                    this.gameState,
                    this.score,
                    Optional.of(getLastRound().getHint()),
                    getLastRound().getLastFeedback(),
                    this.rounds.size(),
                    Optional.empty());
        } else if (this.gameState == ELIMINATED) {
            return new Progress(this.id,
                    this.gameState,
                    this.score,
                    Optional.empty(),
                    getLastRound().getLastFeedback(),
                    this.rounds.size(),
                    Optional.of(getLastRound().getWordToBeGuessed()));
        }
            return new Progress(this.id,
                                this.gameState,
                                this.score,
                                Optional.empty(),
                                Optional.empty(),
                                this.rounds.size(),
                                Optional.empty());
    }

    public Round getLastRound(){
        if(!rounds.isEmpty()) {
            return rounds.get(rounds.size() - 1);
        }
        return null;
    }

    public long getId() {
        return id;
    }
}
