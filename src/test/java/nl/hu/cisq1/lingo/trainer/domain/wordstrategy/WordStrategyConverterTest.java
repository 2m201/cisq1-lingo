package nl.hu.cisq1.lingo.trainer.domain.wordstrategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordStrategyConverterTest {
    final WordStrategyInterface defaultWordStrategy = new DefaultWordStrategy();
    final WordStrategyConverter wordStrategyConverter = new WordStrategyConverter();

    @Test
    @DisplayName("Converting strategy to string")
    void convertingToString(){
        String string = wordStrategyConverter.convertToDatabaseColumn(defaultWordStrategy);

        assertEquals("defaultwordstrategy", string);
    }

    @Test
    @DisplayName("Converting string to strategy")
    void convertingToStrategy(){
        WordStrategyInterface wordStrategy =
                wordStrategyConverter.convertToEntityAttribute("defaultwordstrategy");

        assertTrue(wordStrategy.getClass().isInstance(defaultWordStrategy));
    }

    @Test
    @DisplayName("Converting unknown string to strategy")
    void convertingUnknownStringToStrategy(){
        assertNull(wordStrategyConverter.convertToEntityAttribute("nothing"));
    }

}