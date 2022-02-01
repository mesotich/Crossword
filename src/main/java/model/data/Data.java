package model.data;

import java.util.ArrayList;
import java.util.List;

public abstract class Data {

    private final List<String> storage;

    public Data() {
        storage = new ArrayList<>();
        loadData();
    }

    public List<String> getStorage() {
        return storage;
    }

    protected void addString(String line) {
        if (!storage.contains(line))
            storage.add(line.toUpperCase());
    }

    protected abstract void loadData();
}
