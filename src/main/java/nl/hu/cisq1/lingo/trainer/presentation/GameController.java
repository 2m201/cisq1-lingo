package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.GameService;
import nl.hu.cisq1.lingo.trainer.application.exception.NoGameFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.GuessNotValidException;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import nl.hu.cisq1.lingo.trainer.domain.exception.NoWordPossibleException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundNotMadeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/lingo")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public ResponseEntity<Progress> startGame(){
        Progress progress = this.gameService.startNewGame();

        return new ResponseEntity<>(progress, HttpStatus.CREATED);
    }

    @PostMapping("/guess/{id}/{attempt}")
    public ResponseEntity<Progress> takeGuess(@PathVariable Long id, @PathVariable String attempt) {
        try {
            Progress progress = gameService.takeAGuess(id, attempt);

            return new ResponseEntity<>(progress, HttpStatus.OK);
        } catch (GuessNotValidException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/start/round/{id}")
    public ResponseEntity<Progress> startRound(@PathVariable Long id) {
            Progress progress = this.gameService.startNewRound(id);

            return new ResponseEntity<>(progress, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Progress> getGame(@PathVariable Long id) {
        return new ResponseEntity<>(this.gameService.findGame(id), HttpStatus.OK);
    }

    @ExceptionHandler(value = GuessNotValidException.class)
    public ResponseEntity<Map<String, String>> guessNotValidHandler(GuessNotValidException GNV) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Error",  GNV.getMessage());

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RoundNotMadeException.class)
    public ResponseEntity<Map<String, String>> roundNotMadeHandler(RoundNotMadeException RNM) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Error",  RNM.getMessage());

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoGameFoundException.class)
    public ResponseEntity<Map<String, String>> noGameFoundHandler(NoGameFoundException NGM) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Error",  NGM.getMessage());

        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NoWordPossibleException.class)
    public ResponseEntity<Map<String, String>> noWordPossibleHandler(NoWordPossibleException NWP) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Error",  NWP.getMessage());

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidFeedbackException.class)
    public ResponseEntity<Map<String, String>> invalidFeedbackHandler(InvalidFeedbackException IF) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Error",  IF.getMessage());

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

}
