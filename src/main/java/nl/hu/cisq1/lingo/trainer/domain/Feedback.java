package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;

import java.util.List;
import java.util.Objects;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.CORRECT;
import static nl.hu.cisq1.lingo.trainer.domain.Mark.INVALID;

public class Feedback {
    private final String attempt;
    private final List<Mark> marks;

    public Feedback(String attempt, List<Mark> marks) {
        if (attempt.length() != marks.size()) {
            throw new InvalidFeedbackException();
        }

        this.attempt = attempt;
        this.marks = marks;
    }

    public boolean isWordGuessed(){
        return marks.stream().allMatch(mark -> mark == CORRECT);

//        for(Mark mark : marks) {
//            if (mark != Mark.CORRECT) {
//                return false;
//            }
//        }
//        return true;
    }

    public boolean isGuessValid () {
        return marks.stream().noneMatch(mark -> mark == INVALID);
    }

    public boolean isGuessInvalid(){
        return marks.stream().allMatch(mark -> mark == INVALID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return attempt.equals(feedback.attempt) &&
                marks.equals(feedback.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, marks);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "attempt='" + attempt + '\'' +
                ", marks=" + marks +
                '}';
    }

    public List<Character> giveHint(String wordToGuess, List<Character> previousHint){
        Hint hint = new Hint(wordToGuess, this.marks, previousHint);

        return hint.getNewHint();
    }

}
