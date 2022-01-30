package model;


import java.util.Set;

public interface Model {

    void startBusinessLogic();

    int size();

    Set<Snapshot> getSnapshots();
}
