import controller.Controller;
import controller.SimpleController;
import model.*;
import model.data.ConsoleData;
import model.data.Data;
import view.ConsoleView;
import view.View;

import java.util.Set;

public class Crossword {

    public static void main(String[] args) {
        Data data = new ConsoleData();
        Model model = new SimpleModel(data);
        Controller controller = new SimpleController(model);
        View view = new ConsoleView();
        controller.start();
        Set<Snapshot> list = model.getSnapshots();
        for (Snapshot snapshot : list
        ) {
            view.showCrossword(snapshot);
        }
        System.out.println("size = "+list.size());
    }
}
