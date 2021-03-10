package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;

public class Feedback {
    private final List<Character> attempt;
    private final Word wordToGuess;
    private final  List<Mark> marks;

    public Feedback(String attempt, Word wordToGuess) {
        this.attempt = new ArrayList<>();
        for (Character letter : attempt.toCharArray()) {
            this.attempt.add(letter);
        }
        this.wordToGuess = wordToGuess;
        this.marks = new ArrayList<>();

        createMarkList();
    }

    private void createMarkList(){
        List<Character> wordToGuessCharacterList = this.wordToGuess.getSpelling();

        if (checkIfGuessValid(wordToGuessCharacterList)) {
            int index = 0;
            for (Character character : this.attempt) {

                if (character.equals(wordToGuessCharacterList.get(index))) {
                    this.marks.add(CORRECT);
                } else if (wordToGuessCharacterList.contains(character) &&
                        !character.equals( wordToGuessCharacterList.get(index))) {
                    calculatePresentForMarks(wordToGuessCharacterList, character);
                } else {
                    this.marks.add(ABSENT);
                }
                index += 1;
            }
        }
    }

    private boolean checkIfGuessValid(List<Character> wordToGuess){
        if (this.attempt.size() != wordToGuess.size() || !this.attempt.get(0).equals(wordToGuess.get(0))) {
            this.attempt.forEach(character -> this.marks.add(INVALID));

            return false;
        }
        return true;
    }

    private void calculatePresentForMarks(List<Character> wordToGuessList, Character character){
        long countListAttempt = this.attempt.stream().filter(character::equals).count();
        long countListPresentMarks = this.marks.stream().filter(mark -> mark == PRESENT).count();
        long countListWordToBeGuessed = wordToGuessList.stream().filter(character::equals).count();
        long countListCorrectMarks = this.marks.stream().filter(mark -> mark == CORRECT).count();
        long countAbsolutePresentMarks = countListPresentMarks + countListCorrectMarks;

        System.out.println(countListAttempt); //2
        System.out.println(countListPresentMarks); // 0
        System.out.println(countListWordToBeGuessed); // 1
        System.out.println(countListCorrectMarks);
        System.out.println(countAbsolutePresentMarks);
        System.out.println("----------------");

        if (countListPresentMarks == countListAttempt ||
                countListAttempt < countListPresentMarks ||
                countListAttempt == 1 && countListWordToBeGuessed == 1
                ) {
            this.marks.add(PRESENT);
        } else {
            this.marks.add(ABSENT);
        }
    }

    public boolean isWordGuessed() {
        return marks.stream().allMatch(mark -> mark == CORRECT);
    }

    public boolean isGuessValid () {
        return marks.stream().noneMatch(mark -> mark == INVALID);
    }

    public List<Mark> getMarks() { return marks; }

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

}
