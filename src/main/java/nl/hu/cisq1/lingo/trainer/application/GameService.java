package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Word;
import nl.hu.cisq1.lingo.trainer.presentation.Progress;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGameException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class GameService {
    private WordService wordService;
    private SpringGameRepository gameRepository;

    private Game game;

    public GameService(WordService wordService, SpringGameRepository gameRepository) {
        this.wordService = wordService;
        this.gameRepository = gameRepository;
    }

    public Progress startNewGame(){
        this.game = new Game();
        Word word = new Word(wordService.provideRandomWord(5));
        this.game.startNewRound(word);
        this.gameRepository.save(this.game);

        return this.game.createProgress();
    }

    public Progress startNewRound(long id){
        findGame(id);

        String wordString;
        int lastWordLength = this.game.getLastRound().getWordLength();

        switch(lastWordLength) {
            case 5:
                wordString = this.wordService.provideRandomWord(6);
                break;
            case 6:
                wordString = this.wordService.provideRandomWord(7);
                break;
            case 7:
                wordString = this.wordService.provideRandomWord(5);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + lastWordLength); //todo create separate exception
        }
        Word word = new Word(wordString);

        game.startNewRound(word);
        gameRepository.save(game);

        return this.game.createProgress();
    }

    public Progress takeAGuess(long id, String attempt){
        findGame(id);

        this.game.takeGuess(attempt);
        this.gameRepository.save(game);

        return this.game.createProgress();
    }

    public Progress findGame(long id){
        Optional<Game> optionalGame = gameRepository.findById(id);

        if (optionalGame.isPresent()) {
            this.game = optionalGame.get();
            return this.game.createProgress();
        } else {
            throw new InvalidGameException();
        }
    }
}
