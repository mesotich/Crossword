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

    public List<Word> getHorizontalList() {
        return horizontalList;
    }

    public List<Word> getVerticalList() {
        return verticalList;
    }
}
