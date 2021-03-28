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
            Progress progress = gameService.takeAGuess(id, attempt);

            return new ResponseEntity<>(progress, HttpStatus.OK);
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
    public ResponseEntity<Map<String, String>> guessNotValidHandler(GuessNotValidException e) {
        return new ResponseEntity<>(createErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RoundNotMadeException.class)
    public ResponseEntity<Map<String, String>> roundNotMadeHandler(RoundNotMadeException e) {
        return new ResponseEntity<>(createErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoGameFoundException.class)
    public ResponseEntity<Map<String, String>> noGameFoundHandler(NoGameFoundException e) {
        return new ResponseEntity<>(createErrorMessage(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NoWordPossibleException.class)
    public ResponseEntity<Map<String, String>> noWordPossibleHandler(NoWordPossibleException e) {
        return new ResponseEntity<>(createErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidFeedbackException.class)
    public ResponseEntity<Map<String, String>> invalidFeedbackHandler(InvalidFeedbackException e) {
        return new ResponseEntity<>(createErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private HashMap<String, String> createErrorMessage(String string) {
        HashMap<String, String> map = new HashMap<>();
        map.put("error",  string);

        return map;
    }

}
