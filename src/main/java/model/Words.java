package model;

import model.data.Data;

import java.util.ArrayList;
import java.util.List;

public class Words {

    private final Data data;
    private List<Word> horizontalList;
    private List<Word> verticalList;

    public Words(Data data) {
        this.data = data;
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

    public List<Word> getIntersection(Word word) {
        List<Word> result = new ArrayList<>();
        List<Word> searchList = word.getDirection().equals(Direction.HORIZONTAL)
                ? verticalList
                : horizontalList;
        for (int i = 0; i < word.getWord().length(); i++) {
            char ch = word.getChar(i);
            for (Word value : searchList) {
                if (value.getWord().contains(String.valueOf(ch)))
                    result.add(value);
            }
        }
        return result;
    }
//   // public List<Word> getIntersection(Word word, int index) {
//        List<Word> result = new ArrayList<>();
//        List<Word> searchList = word.getDirection().equals(Direction.HORIZONTAL)
//                ? verticalList
//                : horizontalList;
//        char ch = word.getChar(index);
//        for (Word w : searchList
//        ) {
//            if (!word.getWord().equals(w.getWord()) && w.containsChar(ch))
//                result.add(w);
//        }
//        return result;
//    }

    public List<Word> getHorizontalList() {
        return horizontalList;
    }

    public List<Word> getVerticalList() {
        return verticalList;
    }
}
