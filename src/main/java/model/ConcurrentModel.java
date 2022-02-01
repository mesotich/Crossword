package model;

import model.data.Data;

import java.util.*;
import java.util.concurrent.*;

public class ConcurrentModel extends SimpleModel {

    public ConcurrentModel(Data data) {
        super(data);
    }

    @Override
    public void startBusinessLogic() {
        initialize();
        createSnapshots();
        snapshots = createThread();
    }

    private Set<Snapshot> createThread() {
        int processors = Runtime.getRuntime().availableProcessors();
        Set<Snapshot> result = null;
        HashSet<Snapshot> set;
        CrosswordThread thread;
        Future<Set<Snapshot>> future;
        List<Future<Set<Snapshot>>> futures = new ArrayList<>();
        ExecutorService es = Executors.newFixedThreadPool((processors > 2) ? processors / 2 : 2);
        for (Snapshot snapshot : snapshots
        ) {
            set = new HashSet<>();
            set.add(snapshot);
            thread = new CrosswordThread(set, this);
            future = es.submit(thread);
            futures.add(future);
        }
        es.shutdown();
        try {
            es.awaitTermination(1L, TimeUnit.DAYS);
            result = new HashSet<>();
            for (int i = 0; i < futures.size(); i++) {
                if (futures.get(i).isDone())
                    result.addAll(futures.get(i).get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }
}
