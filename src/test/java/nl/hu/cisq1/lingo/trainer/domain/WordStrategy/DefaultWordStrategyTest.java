package nl.hu.cisq1.lingo.trainer.domain.WordStrategy;

import nl.hu.cisq1.lingo.trainer.domain.exception.NoWordPossibleException;
import nl.hu.cisq1.lingo.trainer.domain.Word;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultWordStrategyTest {
    WordService wordService = mock(WordService.class);
    final DefaultWordStrategy defaultWordStrategy = new DefaultWordStrategy();


    static Stream<Arguments> provideCorrectLengthExamples() {
        return Stream.of(
                Arguments.of(5,6),
                Arguments.of(6, 7),
                Arguments.of(7, 5)
        );
    }

    static Stream<Arguments> provideInCorrectLengthExamples() {
        return Stream.of(
                Arguments.of(4),
                Arguments.of(8),
                Arguments.of(-100),
                Arguments.of(100)
        );
    }

    @ParameterizedTest
    @MethodSource("provideCorrectLengthExamples")
    @DisplayName("Calculating a word when a valid word length has been given")
    void gettingWordWithCorrectValues(int lastLength, int expectedNewLength){
        when(wordService.provideRandomWord(5)).thenReturn("peren");
        when(wordService.provideRandomWord(6)).thenReturn("honden");
        when(wordService.provideRandomWord(7)).thenReturn("maagsap");

        Word word = defaultWordStrategy.generateNextWord(lastLength, wordService);
        assertEquals(expectedNewLength, word.getSpelling().size());

    }

    @ParameterizedTest
    @MethodSource("provideInCorrectLengthExamples")
    @DisplayName("Calculating a word when an invalid word length has been given")
    void gettingWordWithInCorrectValues(int lastLength){
        assertThrows(NoWordPossibleException.class, () ->
                defaultWordStrategy.generateNextWord(lastLength, wordService));
    }

}