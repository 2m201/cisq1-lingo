package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
