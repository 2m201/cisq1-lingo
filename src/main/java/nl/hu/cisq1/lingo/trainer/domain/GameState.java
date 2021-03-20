package nl.hu.cisq1.lingo.trainer.domain;

import java.io.Serializable;

public enum GameState implements Serializable {
    WAITING_FOR_ROUND,
    PLAYING,
    ELIMINATED
}
