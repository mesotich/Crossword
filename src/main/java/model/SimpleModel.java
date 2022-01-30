package model;

import model.data.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleModel implements Model {

    private Data data;
    private Words words;
    private Set<Snapshot> snapshots;

    public SimpleModel(Data data) {
        this.data = data;
    }

    @Override
    public void startBusinessLogic() {
        initialize();
        createSnapshots();
        insertWords();
        insertWords();
        insertWords();
        insertWords();
    }

    private void createSnapshots() {
        List<Word> wordList = words.getHorizontalList();
        for (Word word : wordList
        ) {
            snapshots.add(new Snapshot(word));
        }
    }

    private void insertWords() {
        boolean isEnd = true;
        Set<Snapshot> newSnapshots = new HashSet<>();
        List<Word> insertWords = new ArrayList<>(words.getHorizontalList());
        insertWords.addAll(words.getVerticalList());
        Set<Word> wordsInSnapshot;
        List<Coordinate> coordinates;
        Snapshot newSnapshot;
        for (Snapshot snapshot : snapshots
        ) {
            wordsInSnapshot = snapshot.getMap().keySet();
            for (Word snapWord : wordsInSnapshot
            ) {
                for (Word insWord : insertWords
                ) {
                    coordinates = snapshot.startCoordinatesForInsert(insWord, snapWord);
                    for (Coordinate coordinate : coordinates
                    ) {
                        if (snapshot.canInsert(insWord, coordinate)) {
                            newSnapshot = new Snapshot(snapshot);
                            newSnapshot.addWord(insWord, coordinate);
                            isEnd = false;
                            newSnapshots.add(newSnapshot);
                        }
                    }
                }
            }
        }
        if (!isEnd) {
            snapshots = newSnapshots;
        }
    }

    private void initialize() {
        words = new Words(data);
        words.fillLists();
        snapshots = new HashSet<>();
    }

    @Override
    public int size() {
        return snapshots.size();
    }

    @Override
    public Set<Snapshot> getSnapshots() {
        return snapshots;
    }
}
