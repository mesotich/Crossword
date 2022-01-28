package model;

import model.data.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SimpleModel implements Model {

    private Data data;
    private Words words;
    private List<Snapshot> snapshots;

    public SimpleModel(Data data) {
        this.data = data;
    }

    @Override
    public void startBusinessLogic() {
        initialize();
        createSnapshots();
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
        List<Snapshot> newSnapshots = new ArrayList<>();
        List<Word> insertWords = new ArrayList<>(words.getHorizontalList());
        insertWords.addAll(words.getVerticalList());
        Set<Word> wordsInSnapshot;
        List<Coordinate> coordinates;
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
//                        if (snapshot.canInsert(insWord, coordinate)) {
//                            Snapshot newSnapshot = new Snapshot(snapshot);
//                            newSnapshot.addWord(insWord, coordinate);
//                            newSnapshots.add(newSnapshot);
//                        }
                        snapshot.addWord(insWord,coordinate);
                    }
                }
            }
        }
        //snapshots = newSnapshots;
    }

    private void initialize() {
        words = new Words(data);
        words.fillLists();
        snapshots = new ArrayList<>();
    }

    @Override
    public int size() {
        return snapshots.size();
    }

    @Override
    public List<Snapshot> getSnapshots() {
        return snapshots;
    }
}
