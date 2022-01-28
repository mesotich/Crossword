package model.data;

import model.data.Data;
import utils.ConsoleHelper;

public class ConsoleData extends Data {

    @Override
    protected void loadData() {
        ConsoleHelper.printMessage("Вводите слова для добавления в кроссворд");
        ConsoleHelper.printMessage("Для выхода нажмите Enter");
        String line = ConsoleHelper.readString();
        while (!line.isEmpty()) {
            addString(line);
            line = ConsoleHelper.readString();
        }
    }
}
