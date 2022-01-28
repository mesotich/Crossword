package model;

import java.util.List;

public interface Model {

    void startBusinessLogic();

    int size();

    List<Snapshot> getSnapshots();
}
