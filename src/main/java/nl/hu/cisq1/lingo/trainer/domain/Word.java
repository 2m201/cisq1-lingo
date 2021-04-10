package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "word")
public class Word {
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String wordList;

    public Word(){}
    public Word(String word) {
        this.wordList = word;
    }

    public List<Character> getSpelling() {
        List<Character> spelling = new ArrayList<>();


        for (Character letter : wordList.toUpperCase().toCharArray()) {
            spelling.add(letter);
        }
        return spelling;
    }
}
