package nl.hu.cisq1.lingo.trainer.domain.WordStrategy;

import nl.hu.cisq1.lingo.trainer.domain.Word;
import nl.hu.cisq1.lingo.words.application.WordService;

public class DefaultWordStrategy implements WordStrategyInterface {
    private WordService wordService;

    public DefaultWordStrategy() { }

    @Override
    public Word generateNextWord(int lastWordLength){
        String wordString;

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
        return new Word(wordString);
    }

    public void setWordService(WordService wordService) {
        this.wordService = wordService;
    }
}
