import controller.Controller;
import controller.SimpleController;
import model.*;
import model.data.ConsoleData;
import model.data.Data;
import model.data.FileData;
import view.ConsoleView;
import view.View;

import java.util.Set;

public class Crossword {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int minSquare = Integer.MAX_VALUE;
        int square;
        Snapshot result = null;
        Data data = new FileData();
        Model model = new SimpleModel(data);
        Controller controller = new SimpleController(model);
        View view = new ConsoleView();
        controller.start();
        Set<Snapshot> list = model.getSnapshots();
        for (Snapshot snapshot : list
        ) {
            square = snapshot.getSquare();
            if(square<minSquare){
                minSquare = square;
                result = snapshot;
            }
        }
        view.showCrossword(result);
        System.out.println("Кол-во вариантов = " + list.size());
        System.out.println("Слов = " + result.size());
        long end = System.currentTimeMillis();
        System.out.println("Время "+(end-start)+" ms");
    }
}
