package model.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileData extends Data {

    private final String stringPath = "C:\\Users\\Дмитрий\\Documents\\Data.txt";

    @Override
    protected void loadData() {
        Path path = Path.of(stringPath);
        try (BufferedReader br = Files.newBufferedReader(path)) {
            while (br.ready()){
                addString(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
