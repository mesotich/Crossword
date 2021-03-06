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
        Map<Word, Coordinate> mapForCopy = snapshot.map;
        this.coordinates = new HashSet<>();
        this.map = new HashMap<>();
        for (Coordinate crd : setForCopy
        ) {
            this.coordinates.add(new Coordinate(crd.getX(), crd.getY(), crd.getCh(), crd.isAllow()));
        }
        for (Map.Entry<Word, Coordinate> entry : mapForCopy.entrySet()
        ) {
            this.map.put(entry.getKey()
                    , new Coordinate(entry.getValue().getX(), entry.getValue().getY(), entry.getValue().getCh(), entry.getValue().isAllow()));
        }
    }

    public int size() {
        return map.size();
    }

    public void addWord(Word word, Coordinate startCoordinate) {
        int x, y;
        Coordinate coordinate;
        for (int i = 0; i < word.getWord().length(); i++) {
            char ch = word.getChar(i);
            if (word.getDirection().equals(Direction.HORIZONTAL)) {
                x = startCoordinate.getX() + i;
                y = startCoordinate.getY();
            } else {
                x = startCoordinate.getX();
                y = startCoordinate.getY() + i;
            }
            coordinate = getCoordinate(x, y);
            if (coordinate != null) {
                if (coordinate.getCh() != ' ')
                    offNeighbours(coordinate);
                coordinate.setCh(ch);
            } else {
                coordinate = new Coordinate(x, y, ch, true);
                coordinates.add(coordinate);
            }
        }
        offStartEnd(word, startCoordinate);
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
        if (containsWord(word))
            return false;
        Coordinate coordinate;
        if (word.getDirection().equals(Direction.HORIZONTAL)) {
            coordinate = getCoordinate(startCoordinate.getX() - 1, startCoordinate.getY());
            if (coordinate != null && coordinate.getCh() != ' ')
                return false;
            coordinate = getCoordinate(startCoordinate.getX() + word.getWord().length(), startCoordinate.getY());
        } else {
            coordinate = getCoordinate(startCoordinate.getX(), startCoordinate.getY() - 1);
            if (coordinate != null && coordinate.getCh() != ' ')
                return false;
            coordinate = getCoordinate(startCoordinate.getX(), startCoordinate.getY() + word.getWord().length());
        }
        if (coordinate != null && coordinate.getCh() != ' ')
            return false;
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
                    coordinate = getCoordinate(x0, y0);
                    if (coordinate == null)
                        coordinate = new Coordinate(x0, y0);
                    result.add(coordinate);
                }
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

    private boolean containsWord(Word word) {
        for (Map.Entry<Word, Coordinate> entry : map.entrySet()
        ) {
            if (entry.getKey().getWord().equals(word.getWord())) {
                return true;
            }
        }
        return false;
    }

    private void offNeighbours(Coordinate coordinate) {
        Coordinate crd;
        int x = coordinate.getX();
        int y = coordinate.getY();
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                crd = getCoordinate(j, i);
                if (crd == null) {
                    crd = new Coordinate(j, i, ' ', false);
                    coordinates.add(crd);
                }
                crd.setAllow(false);
            }
        }
    }

    private void offStartEnd(Word word, Coordinate startCoordinate) {
        Coordinate beforeCoordinate;
        Coordinate afterCoordinate;
        int x1, y1, x2, y2;
        for (int i = -1; i <= 1; i++) {
            if (word.getDirection().equals(Direction.HORIZONTAL)) {
                x1 = startCoordinate.getX() - 1;
                y1 = y2 = startCoordinate.getY() + i;
                x2 = startCoordinate.getX() + word.getWord().length();
            } else {
                x1 = x2 = startCoordinate.getX() + i;
                y1 = startCoordinate.getY() - 1;
                y2 = startCoordinate.getY() + word.getWord().length();
            }
            beforeCoordinate = getCoordinate(x1, y1);
            afterCoordinate = getCoordinate(x2, y2);
            if (beforeCoordinate != null)
                beforeCoordinate.setAllow(false);
            else {
                beforeCoordinate = new Coordinate(x1, y1, ' ', false);
                coordinates.add(beforeCoordinate);
            }
            if (afterCoordinate != null)
                afterCoordinate.setAllow(false);
            else {
                afterCoordinate = new Coordinate(x2, y2, ' ', false);
                coordinates.add(afterCoordinate);
            }
        }
    }

    public int getSquare() {
        int minX = 0, minY = 0;
        int maxX = 0, maxY = 0;
        int x, y;
        char ch;
        for (Coordinate crd : coordinates
        ) {
            x = crd.getX();
            y = crd.getY();
            ch = crd.getCh();
            if (ch != ' ') {
                if (x < minX)
                    minX = x;
                if (x > maxX)
                    maxX = x;
                if (y < minY)
                    minY = y;
                if (y > maxY)
                    maxY = y;
            }
        }
        return (maxX - minX + 1) * (maxY - minY + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Snapshot snapshot = (Snapshot) o;
        return this.coordinates.equals(snapshot.coordinates) && this.map.equals(snapshot.map);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + coordinates.hashCode();
        result = prime * result + map.hashCode();
        return result;
    }
}
