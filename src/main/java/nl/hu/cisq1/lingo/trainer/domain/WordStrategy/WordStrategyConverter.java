package nl.hu.cisq1.lingo.trainer.domain.WordStrategy;


import javax.persistence.AttributeConverter;

public class WordStrategyConverter implements AttributeConverter<WordStrategyInterface, String> {
    @Override
    public String convertToDatabaseColumn(WordStrategyInterface wordStrategyInterface) {
        return wordStrategyInterface.getClass().getSimpleName().toLowerCase();
    }

    @Override
    public WordStrategyInterface convertToEntityAttribute(String dbData) {
        if(dbData.equals("defaultwordstrategy")){
            return new DefaultWordStrategy();
        }
        return null;
    }


}
