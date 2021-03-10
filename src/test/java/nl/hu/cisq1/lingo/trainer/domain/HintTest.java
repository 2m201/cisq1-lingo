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
    static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of("brood", List.of('b', '.', '.', '.', '.'), "brouw", List.of('b', 'r', 'o', '.', '.')),
                Arguments.of("brood", List.of('b', 'r', '.', '.', '.'), "broek", List.of('b', 'r', 'o', '.', '.')),
                Arguments.of("brood", List.of('b', 'r', '.', 'o', '.'), "vloer", List.of('b', 'r', '.', 'o', '.')),
                Arguments.of("brood", List.of('b', '.', '.', '.', '.'), "bongo", List.of('b', '.', '.', '.', '.'))
        );
    }

    @Test
    @DisplayName("Creating the first hint when a new round has begun")
    void createFirstHint(){
        Word word = new Word("hallo");
        Round round = new Round(word);

        assertEquals(List.of('h', '.', '.','.','.'), round.getBeginHint().getNewHint());
    }

}