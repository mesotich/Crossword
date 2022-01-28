import controller.Controller;
import controller.SimpleController;
import model.*;
import model.data.ConsoleData;
import model.data.Data;
import view.ConsoleView;
import view.View;

import java.util.ArrayList;
import java.util.List;

public class Crossword {

    public static void main(String[] args) {
        Data data = new ConsoleData();
        Model model = new SimpleModel(data);
        Controller controller = new SimpleController(model);
        View view = new ConsoleView();
        controller.start();
        System.out.println("size = "+model.size());
        List<Snapshot> list = model.getSnapshots();
        for (Snapshot snapshot:list
             ) {
            view.showCrossword(snapshot);
        }
    }
}
