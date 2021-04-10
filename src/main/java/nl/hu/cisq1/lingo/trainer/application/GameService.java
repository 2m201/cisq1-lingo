package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.application.exception.NoGameFoundException;
import nl.hu.cisq1.lingo.trainer.presentation.Progress;
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

        this.game.startNewRound(wordService);
        this.gameRepository.save(this.game);

        return this.game.createProgress();
    }

    public Progress startNewRound(Long id){
        this.findGame(id);

        this.game.startNewRound(wordService);
        this.gameRepository.save(this.game);

        return this.game.createProgress();
    }

    public Progress takeAGuess(long id, String attempt){
        this.findGame(id);

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
            throw new NoGameFoundException("No game was found with ID: " + id);
        }
    }
}
