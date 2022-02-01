package model;

import model.data.Data;

import java.util.*;

public class Words {

    private final Data data;
    private List<Word> horizontalList;
    private List<Word> verticalList;
    private Map<Word, Set<Word>> map;

    public Words(Data data) {
        this.data = data;
    }

    public void initialize() {
        fillLists();
        fillMap();
    }

    public void fillLists() {
        horizontalList = new ArrayList<>();
        verticalList = new ArrayList<>();
        List<String> list = data.getStorage();
        for (String s : list
        ) {
            horizontalList.add(new Word(s, Direction.HORIZONTAL));
            verticalList.add(new Word(s, Direction.VERTICAL));
        }
    }

    public List<Word> getHorizontalList() {
        return horizontalList;
    }

    public List<Word> getVerticalList() {
        return verticalList;
    }

    private void fillMap() {
        Set<Word> words;
        map = new HashMap<>();
        for (Word word : horizontalList
        ) {
            words = getIntersections(word);
            map.put(word, words);
        }
        for (Word word : verticalList
        ) {
            words = getIntersections(word);
            map.put(word, words);
        }
    }

    private Set<Word> getIntersections(Word word) {
        Set<Word> result = new HashSet<>();
        List<Word> words;
        if (word.getDirection().equals(Direction.HORIZONTAL))
            words = verticalList;
        else words = horizontalList;
        for (int i = 0; i < word.getWord().length(); i++) {
            for (Word value : words) {
                if (value.containsChar(word.getChar(i)))
                    result.add(value);
            }
        }
        return result;
    }

    public Map<Word, Set<Word>> getMap() {
        return map;
    }
}
