package model;

import java.util.*;

public class Snapshot {

    private Set<Coordinate> coordinates;
    private Map<Word, Coordinate> map;

    public Snapshot(Word firstWord) {
        coordinates = new HashSet<>();
        map = new HashMap<>();
        Coordinate coordinate = new Coordinate(0, 0);
        addWord(firstWord, coordinate);
    }

    public Snapshot(Snapshot snapshot) {
        Set<Coordinate> setForCopy = snapshot.coordinates;
        Map<Word,Coordinate> mapForCopy = snapshot.map;
        this.coordinates = new HashSet<>();
        this.map = new HashMap<>();
        for (Coordinate crd:setForCopy
             ) {
            this.coordinates.add(new Coordinate(crd.getX(),crd.getY(),crd.getCh(),crd.isAllow()));
        }
        for (Map.Entry<Word,Coordinate> entry:mapForCopy.entrySet()
             ) {
            this.map.put(entry.getKey()
                    ,new Coordinate(entry.getValue().getX(),entry.getValue().getY(),entry.getValue().getCh(), entry.getValue().isAllow()));
        }
    }

    public int size() {
        return map.size();
    }

    public void addWord(Word word, Coordinate startCoordinate) {
        if (!canInsert(word, startCoordinate))
            return;
        Coordinate coordinate;
        for (int i = 0; i < word.getWord().length(); i++) {
            char ch = word.getChar(i);
            if (word.getDirection().equals(Direction.HORIZONTAL))
                coordinate = new Coordinate(startCoordinate.getX() + i, startCoordinate.getY(), ch, true);
            else
                coordinate = new Coordinate(startCoordinate.getX(), startCoordinate.getY() + i, ch, true);
            coordinates.add(coordinate);
        }
        startCoordinate.setCh(word.getChar(0));
        map.put(word, startCoordinate);
    }

    public Coordinate getCoordinate(int x, int y) {
        Coordinate coordinate = new Coordinate(x, y);
        Coordinate result;
        for (Coordinate value : coordinates) {
            result = value;
            if (coordinate.equals(result))
                return result;
        }
        return null;
    }

    public boolean canInsert(Word word, Coordinate startCoordinate) {
        if (map.containsKey(word))
            return false;
        Coordinate coordinate;
        for (int i = 0; i < word.getWord().length(); i++) {
            char ch = word.getChar(i);
            if (word.getDirection().equals(Direction.HORIZONTAL))
                coordinate = getCoordinate(startCoordinate.getX() + i, startCoordinate.getY());
            else coordinate = getCoordinate(startCoordinate.getX(), startCoordinate.getY() + i);
            if (coordinate != null && (!coordinate.isAllow() || (coordinate.getCh() != ' ' && coordinate.getCh() != ch)))
                return false;
        }
        return true;
    }

    public List<Coordinate> getWordCoordinates(Word word) {
        Coordinate coordinate = map.get(word);
        int x = coordinate.getX();
        int y = coordinate.getY();
        List<Coordinate> result = new ArrayList<>();
        result.add(coordinate);
        Direction direction = word.getDirection();
        for (int i = 1; i < word.getWord().length(); i++) {
            if (direction.equals(Direction.HORIZONTAL)) {
                coordinate = getCoordinate(x + i, y);
            } else coordinate = getCoordinate(x, y + 1);
            result.add(coordinate);
        }
        return result;
    }

    public List<Coordinate> startCoordinatesForInsert(Word insertWord, Word snapshotWord) {
        int x, y;
        int x0 = 0, y0 = 0;
        List<Coordinate> result = new ArrayList<>();
        Coordinate coordinate;
        if (!map.containsKey(snapshotWord) || map.containsKey(insertWord)
                || insertWord.getDirection().equals(snapshotWord.getDirection())
                || insertWord.getWord().equals(snapshotWord.getWord()))
            return result;
        List<Coordinate> coordinateList = getWordCoordinates(snapshotWord);
        for (int i = 0; i < coordinateList.size(); i++) {
            x = coordinateList.get(i).getX();
            y = coordinateList.get(i).getY();
            for (int j = 0; j < insertWord.getWord().length(); j++) {
                if (snapshotWord.getChar(i) == insertWord.getChar(j)) {
                    if (insertWord.getDirection().equals(Direction.VERTICAL)) {
                        x0 = x;
                        y0 = y - j;
                    } else {
                        x0 = x - j;
                        y0 = y;
                    }
                }
                coordinate = getCoordinate(x0, y0);
                if (coordinate == null)
                    coordinate = new Coordinate(x0, y0);
                result.add(coordinate);
                coordinates.add(coordinate);
            }
        }
        return result;
    }

    public Set<Coordinate> getCoordinates() {
        return coordinates;
    }

    public Map<Word, Coordinate> getMap() {
        return map;
    }
}
