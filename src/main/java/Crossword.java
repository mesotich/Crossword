import controller.Controller;
import controller.SimpleController;
import model.*;
import model.data.Data;
import model.data.FileData;
import view.ConsoleView;
import view.View;

import java.util.Set;

public class Crossword {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int maxSize = 0;
        int size = 0;
        int minSquare = Integer.MAX_VALUE;
        int square = 0;
        Snapshot result = null;
        Data data = new FileData();
        Model model = new ConcurrentModel(data);
        Controller controller = new SimpleController(model);
        View view = new ConsoleView();
        controller.start();
        Set<Snapshot> list = model.getSnapshots();
        for (Snapshot snapshot : list
        ) {
            size = snapshot.size();
            square = snapshot.getSquare();
            if (size >= maxSize && square < minSquare) {
                minSquare = square;
                maxSize = size;
                result = snapshot;
            }
        }
        view.showCrossword(result);
        System.out.println("Кол-во вариантов = " + list.size());
        System.out.println("Слов = " + result.size());
        long end = System.currentTimeMillis();
        System.out.println("Время " + (end - start) + " ms");
        System.out.println("Площадь кроссворда = " + result.getSquare() + " кл");
    }
}
