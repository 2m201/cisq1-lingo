package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;

public class Feedback {
    private final String attempt;
    private final Word wordToGuess;
    private List<Mark> marks;
    private Hint hint;

    public Feedback(String attempt, Word wordToGuess, List<Character> previousHint) {
        this.attempt = attempt;
        this.wordToGuess = wordToGuess;
        this.marks = new ArrayList<>();

        createMarkList();

        this.hint = new Hint(wordToGuess, this.marks, previousHint);
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

    private List<Mark> createMarkList(){
        List<Character> attemptCharacterList = new ArrayList<>();
        List<Character> wordToGuessCharacterList = this.wordToGuess.getSpelling();

        for (Character letter : attempt.toCharArray()) {
            attemptCharacterList.add(letter);
        }

        if (attempt.length() != wordToGuessCharacterList.size() || attemptCharacterList.get(0) != wordToGuessCharacterList.get(0)) {
            attemptCharacterList.forEach(character -> this.marks.add(INVALID));
        } else {
            int index = 0;
            for (Character character : attemptCharacterList) {
                long countListAttempt = 0;
                long countListPresentMarks = 0;
                long countListCorrectMarks = 0;

                if (character == wordToGuessCharacterList.get(index)) {
                    this.marks.add(CORRECT);
                    countListPresentMarks += 1;
                } else if (wordToGuessCharacterList.contains(character) && character != wordToGuessCharacterList.get(index))  {
                    countListAttempt += attemptCharacterList.stream().filter(character::equals).count();
                    countListPresentMarks += this.marks.stream().filter(mark -> mark == PRESENT).count();
                    countListCorrectMarks += this.marks.stream().filter(mark -> mark == CORRECT).count();
                    long countListWordToBeGuessed = wordToGuessCharacterList.stream().filter(character::equals).count();

                    if (countListPresentMarks == countListAttempt ||
                            countListAttempt < countListPresentMarks ||
                            countListAttempt == 1 && countListWordToBeGuessed == 1) {
                        this.marks.add(PRESENT);
                    }else {
                        this.marks.add(ABSENT);
                    }
                } else {
                    this.marks.add(ABSENT);
                }
                index += 1;
            }
        }

        return this.marks;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public Hint getHint() {
        return hint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(attempt, feedback.attempt) &&
                Objects.equals(wordToGuess, feedback.wordToGuess) &&
                Objects.equals(marks, feedback.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, wordToGuess, marks);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "attempt='" + attempt + '\'' +
                ", wordToGuess=" + wordToGuess +
                ", marks=" + marks +
                '}';
    }

    //todo add equals, hashcode and tostring back
}
