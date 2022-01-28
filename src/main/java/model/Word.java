package model;

import model.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Word {

    private final String word;
    private final Direction direction;

    public Word(String word, Direction direction) {
        this.word = word;
        this.direction = direction;
    }

    public char getChar(int index) {
        return word.charAt(index);
    }

    public boolean containsChar(char ch) {
        return word.contains(String.valueOf(ch));
    }

    public List<Integer> getIndexes(char ch) {
        List<Integer> result = new ArrayList<>();
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ch)
                result.add(i);
        }
        return result;
    }

    public String getWord() {
        return word;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return Objects.equals(word, word1.word) && direction == word1.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, direction);
    }

    @Override
    public String toString() {
        return word + ":" + direction;
    }
}

