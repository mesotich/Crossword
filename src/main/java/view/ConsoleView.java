package view;

import model.Coordinate;
import model.Direction;
import model.Snapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ConsoleView implements View {

    private Snapshot snapshot;
    private char[][] matrix;
    private int minX, maxX;
    private int minY, maxY;

    @Override
    public void showCrossword(Snapshot snapshot) {
        deleteMinMax();
        this.snapshot = snapshot;
        findMinMax();
        createMatrix();
        deleteEmptyLines();
        printMatrix();
    }

    private void deleteMinMax() {
        minX = 0;
        maxX = 0;
        minY = 0;
        maxY = 0;
    }

    private void findMinMax() {
        int x, y;
        Set<Coordinate> coordinates = snapshot.getCoordinates();
        for (Coordinate crd : coordinates
        ) {
            x = crd.getX();
            y = crd.getY();
            if (x > maxX)
                maxX = x;
            if (x < minX)
                minX = x;
            if (y > maxY)
                maxY = y;
            if (y < minY)
                minY = y;
        }
    }

    private void createMatrix() {
        int x, y;
        int dx = -(minX);
        int dy = -(minY);
        matrix = new char[maxY - minY + 1][maxX - minX + 1];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = ' ';
            }
        }
        Set<Coordinate> coordinates = snapshot.getCoordinates();
        for (Coordinate crd : coordinates
        ) {
            x = crd.getX();
            y = crd.getY();
            matrix[y + dy][x + dx] = crd.getCh();
        }
    }

    private void deleteEmptyLines() {
        List<List<Character>> list = convertToLists();
        list.removeIf(line -> line.stream().allMatch(e -> e.equals(' ')));
        convertToMatrix(list);
        rotate90();
        list = convertToLists();
        list.removeIf(line -> line.stream().allMatch(e -> e.equals(' ')));
        convertToMatrix(list);
        rotate90();
        rotate90();
        rotate90();
    }

    private List<List<Character>> convertToLists() {
        List<List<Character>> result = new ArrayList<>();
        List<Character> line;
        for (int i = 0; i < matrix.length; i++) {
            line = new ArrayList<>();
            for (int j = 0; j < matrix[0].length; j++) {
                line.add(matrix[i][j]);
            }
            result.add(line);
        }
        return result;
    }

    private void convertToMatrix(List<List<Character>> list) {
        matrix = new char[list.size()][list.get(0).size()];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(0).size(); j++) {
                matrix[i][j] = list.get(i).get(j);
            }
        }
    }

    private void rotate90() {
        char[][] result = new char[matrix[0].length][matrix.length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = matrix[matrix.length - j - 1][i];
            }
        }
        matrix = result;
    }

    private void printMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println("========================================");
    }


}
