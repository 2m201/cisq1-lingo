package nl.hu.cisq1.lingo.trainer.domain.WordStrategy;

import nl.hu.cisq1.lingo.trainer.domain.exception.NoWordPossibleException;
import nl.hu.cisq1.lingo.trainer.domain.Word;
import nl.hu.cisq1.lingo.words.application.WordService;

public class DefaultWordStrategy implements WordStrategyInterface {

    public DefaultWordStrategy() { }

            @Override
            public Word generateNextWord(int lastWordLength, WordService wordService){
                String wordString;

                switch(lastWordLength) {
                    case 5:
                        wordString = wordService.provideRandomWord(6);
                        break;
                    case 6:
                wordString = wordService.provideRandomWord(7);
                break;
            case 7:
                wordString = wordService.provideRandomWord(5);
                break;
            default:
                throw new NoWordPossibleException("Cannot generate word with given length:  " + lastWordLength);
        }
        return new Word(wordString);
    }

}
