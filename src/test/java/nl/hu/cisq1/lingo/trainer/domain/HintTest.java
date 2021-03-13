package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HintTest {
    private static Word word = new Word("PAARD");

    static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of(List.of(new Feedback( "PEREN", word),
                        new Feedback("PLOOI", word)),
                        List.of('P', '.','.','.','.')),
                Arguments.of(List.of(new Feedback( "PEREN", word),
                        new Feedback("PLOOI", word),
                        new Feedback("PADDO", word)),
                        List.of('P', 'A','.','.','.')),
                Arguments.of(List.of(new Feedback( "PEREN", word),
                        new Feedback("PLOOI", word),
                        new Feedback("PADDO", word),
                        new Feedback("PLAAG", word)),
                        List.of('P', 'A','A','.','.')),
                Arguments.of(List.of(new Feedback( "PEREN", word),
                        new Feedback("PLOOI", word),
                        new Feedback("PADDO", word),
                        new Feedback("PLAAG", word),
                        new Feedback("ZWOER", word)),
                        List.of('P', 'A','A','.','.')),
                Arguments.of(List.of(new Feedback( "PEREN", word),
                        new Feedback("PLOOI", word),
                        new Feedback("PADDO", word),
                        new Feedback("PLAAG", word),
                        new Feedback("ZWOER", word),
                        new Feedback("PAARD", word)),
                        List.of('P', 'A','A','R','D'))
        );
    }

    @Test
    @DisplayName("Creating the first hint when a new round has begun")
    void createFirstHint(){
        Word word = new Word("hallo");
        Round round = new Round(word);

        assertEquals(List.of('h', '.', '.','.','.'), round.getBeginHint().getNewHint());
    }

    @ParameterizedTest
    @MethodSource("provideHintExamples")
    @DisplayName("Creating a hint based on feedback list")
    void createHintBasedOnFeedbackList( List<Feedback> feedbacks, List<Character> expectedHint){
        Hint hint = new Hint(word, feedbacks);

        assertEquals(expectedHint, hint.getNewHint());
    }
}