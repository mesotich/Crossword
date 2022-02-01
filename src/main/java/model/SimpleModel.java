package model;

import model.data.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleModel implements Model {

    protected Data data;
    protected Words words;
    protected Set<Snapshot> snapshots;

    public SimpleModel(Data data) {
        this.data = data;
    }

    @Override
    public void startBusinessLogic() {
        initialize();
        createSnapshots();
        snapshots = getResult(snapshots);
    }

    protected void createSnapshots() {
        List<Word> wordList = words.getHorizontalList();
        for (Word word : wordList
        ) {
            snapshots.add(new Snapshot(word));
        }
    }

    private Set<Snapshot> insertWords(Set<Snapshot> snapshots) {
        Set<Snapshot> newSnapshots = new HashSet<>();
        Set<Word> wordsInSnapshot;
        Set<Word> insertWords;
        List<Coordinate> coordinates;
        Snapshot newSnapshot;
        for (Snapshot snapshot : snapshots
        ) {
            wordsInSnapshot = snapshot.getMap().keySet();
            for (Word snapWord : wordsInSnapshot
            ) {
                insertWords = words.getMap().get(snapWord);
                for (Word insWord : insertWords
                ) {
                    coordinates = snapshot.startCoordinatesForInsert(insWord, snapWord);
                    for (Coordinate coordinate : coordinates
                    ) {
                        if (snapshot.canInsert(insWord, coordinate)) {
                            newSnapshot = new Snapshot(snapshot);
                            newSnapshot.addWord(insWord, coordinate);
                            newSnapshots.add(newSnapshot);
                        }
                    }
                }
            }
        }
        return newSnapshots;
    }

    protected Set<Snapshot> getResult(Set<Snapshot> snapshots) {
        Set<Snapshot> result = null;
        while (snapshots.size() != 0) {
            snapshots = insertWords(snapshots);
            if (snapshots.size() != 0) {
                result = snapshots;
            }
        }
        return result;
    }

    protected void initialize() {
        words = new Words(data);
        words.initialize();
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

    private boolean toBig(Snapshot snapshot, Snapshot newSnapshot) {
        return newSnapshot.getSquare() > snapshot.getSquare() * 4;
    }
}
