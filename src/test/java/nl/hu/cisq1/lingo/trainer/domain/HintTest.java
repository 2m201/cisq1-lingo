package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HintTest {
    private static Word word = new Word("PAARD");

    static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of(List.of(new Feedback( "PEREN", word)),
                        List.of('P', '.','.','.','.')),
                Arguments.of(List.of(new Feedback( "PEREN", word),
                        new Feedback("PLOOI", word)),
                        List.of('P', '.','.','.','.')),
                Arguments.of(List.of(new Feedback( "PEREN", word),
                        new Feedback("PLOOI", word),
                        new Feedback("PADDO", word)),
                        List.of('P', 'A','.','.','.')),
                Arguments.of(List.of(new Feedback( "PEREN", word),
                        new Feedback("PADDO", word),
                        new Feedback("PLOOI", word),
                        new Feedback("ZWOER", word)),
                        List.of('P', 'A','.','.','.')),
                Arguments.of(List.of(new Feedback( "PEREN", word),
                        new Feedback("PLOOI", word),
                        new Feedback("PADDO", word),
                        new Feedback("PLAAG", word),
                        new Feedback("PAARD", word)),
                        List.of('P', 'A','A','R','D'))
        );
    }

    @Test
    @DisplayName("Creating the first hint")
    void createFirstHint(){
        Word word = new Word("hallo");
        List<Feedback> feedbacks = new ArrayList<>();

        Hint hint = new Hint(word, feedbacks);
        assertEquals(List.of('H', '.', '.','.','.'), hint.getNewHint());
    }

    @ParameterizedTest
    @MethodSource("provideHintExamples")
    @DisplayName("Creating a hint with a valid feedback list")
    void createHintWithValidFeedbackList( List<Feedback> feedbacks, List<Character> expectedHint){
        Hint hint = new Hint(word, feedbacks);

        assertEquals(expectedHint, hint.getNewHint());
    }

    @Test
    @DisplayName("Creating a hint with an invalid feedback list")
    void createHintWithInvalidFeedback() {
        Feedback feedback = new Feedback("PERENBOOM", word);

        Hint hint = new Hint(word, List.of(feedback));

        assertEquals(List.of('P', '.','.', '.', '.'), hint.getNewHint());
    }

    @Test
    @DisplayName("Creating a hint when the feedback list size is 6")
    void createHintWhenListSizeIsSix(){
        Feedback feedback = new Feedback("PLOOI", word);
        Feedback feedback2 = new Feedback("PADDO", word);
        Feedback feedback3 = new Feedback("PLAAG", word);
        Feedback feedback4 = new Feedback("PLEIN", word);
        Feedback feedback5 = new Feedback("PREEK", word);
        Feedback feedback6 = new Feedback("PAARD", word);
        List<Feedback> feedbacks =  List.of(feedback, feedback2, feedback3, feedback4, feedback5, feedback6);

        assertThrows(InvalidFeedbackException.class, () -> new Hint(word, feedbacks));
    }
}