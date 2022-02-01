package model;

import java.util.Set;
import java.util.concurrent.Callable;

public class CrosswordThread implements Callable<Set<Snapshot>> {

    private final Set<Snapshot> snapshots;
    private final ConcurrentModel concurrentModel;


    public CrosswordThread(Set<Snapshot> snapshots, ConcurrentModel concurrentModel) {
        this.snapshots = snapshots;
        this.concurrentModel = concurrentModel;
    }

    @Override
    public Set<Snapshot> call() throws Exception {
        return concurrentModel.getResult(snapshots);
    }
}
