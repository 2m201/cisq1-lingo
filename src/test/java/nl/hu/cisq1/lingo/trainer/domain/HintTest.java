package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidHintException;
import org.hibernate.dialect.identity.HANAIdentityColumnSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HintTest {
//    static Stream<Arguments> provideHintExamples() {
//        return Stream.of(
//                Arguments.of("brood", List.of('b', '.', '.', '.', '.'), "brouw", List.of('b', 'r', 'o', '.', '.')),
//                Arguments.of("brood", List.of('b', 'r', '.', '.', '.'), "broek", List.of('b', 'r', 'o', '.', '.')),
//                Arguments.of("brood", List.of('b', 'r', '.', 'o', '.'), "vloer", List.of('b', 'r', '.', 'o', '.')),
//                Arguments.of("brood", List.of('b', '.', '.', '.', '.'), "bongo", List.of('b', '.', '.', '.', '.'))
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideHintExamples")
//    @DisplayName("Feedback gives hint based off of the guess and mark list")
//    void feedbackGivesHint(String wordToGuess, List<Character> previousHint, String attempt, List<Character> expectedHint){
//        Word word = new Word(wordToGuess);
//        Feedback feedback = new Feedback(attempt, word, previousHint);
//
//        assertEquals(expectedHint, feedback.getHint().getNewHint());
//    }
//
//    @Test
//    @DisplayName("Creating the first hint when a new round has begun")
//    void createFirstHint(){
//        Word word = new Word("hello");
//
//        Hint hint = new Hint(word);
//
//        assertEquals(List.of('h', '.', '.','.','.'), hint.getNewHint());
//    }
//
//    @Test
//    @DisplayName("The to be guessed word and the previous hint have different lengths")
//    void previousHintAndToBeGuessedWordDifferentLength(){
//        Word word = new Word("PAARD");
//
//        assertThrows(InvalidHintException.class, () -> new Hint(word, List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT), List.of('P')));
//    }

}