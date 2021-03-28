package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;

@Entity(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue
    private long id;

    @ElementCollection
    private List<Character> attempt;

    @OneToOne(cascade = CascadeType.ALL)
    private Word wordToGuess;

    @ElementCollection
    private List<Mark> marks;

    public Feedback(){}
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
        List<Character> absentCharacters = new ArrayList<>();

        if (checkIfGuessValid(wordToGuessCharacterList)) {
            int index = 0;
            for (Character character : this.attempt) {

                if (character.equals(wordToGuessCharacterList.get(index))) {
                    this.marks.add(CORRECT);
                    wordToGuessCharacterList.set(index, '_');
                }else {
                    absentCharacters.add(character);
                    this.marks.add(ABSENT);
                }
                index += 1;
            }
        }

        for (Character character : this.attempt) {
            if(absentCharacters.contains(character) && wordToGuessCharacterList.contains(character)){
                absentCharacters.remove(character);
                this.marks.set(attempt.indexOf(character), PRESENT);
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
        return id == feedback.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", attempt=" + attempt +
                ", wordToGuess=" + wordToGuess +
                ", marks=" + marks +
                '}';
    }
}
