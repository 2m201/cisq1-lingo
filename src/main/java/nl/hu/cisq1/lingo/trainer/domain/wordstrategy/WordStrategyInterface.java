package nl.hu.cisq1.lingo.trainer.domain.wordstrategy;

import nl.hu.cisq1.lingo.trainer.domain.Word;
import nl.hu.cisq1.lingo.words.application.WordService;

public interface WordStrategyInterface {
    Word generateNextWord(int length, WordService wordService);
}
