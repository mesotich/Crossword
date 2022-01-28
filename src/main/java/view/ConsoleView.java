package view;

import model.Coordinate;
import model.Snapshot;

import java.util.Set;

public class ConsoleView implements View {

    @Override
    public void showCrossword(Snapshot snapshot) {
        int x, y, dx, dy;
        Coordinate coordinate;
        int[][] change = getMinMaxCoordinates(snapshot);
        x = change[1][0] - change[0][0];
        y = change[1][1] - change[0][1];
        dx = -change[0][0];
        dy = -change[0][1];
        char[][] matrix = new char[y + 1][x + 1];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                coordinate = snapshot.getCoordinate(j - dx, i - dy);
                if (coordinate == null)
                    matrix[i][j] = ' ';
                else
                    matrix[i][j] = coordinate.getCh();
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println("===================================");
    }

    private int[][] getMinMaxCoordinates(Snapshot snapshot) {
        int[][] result = new int[2][2];
        int x = 0, y = 0, minX = 0, minY = 0, maxX = 0, maxY = 0;
        Set<Coordinate> coordinates = snapshot.getCoordinates();
        for (Coordinate crd : coordinates
        ) {
            x = crd.getX();
            y = crd.getY();
            if (x <= minX)
                minX = x;
            if (y <= minY)
                minY = y;
            if (x >= maxX)
                maxX = x;
            if (y > maxY)
                maxY = y;
        }
        result[0][0] = minX;
        result[0][1] = minY;
        System.out.println(minY);
        result[1][0] = maxX;
        result[1][1] = maxY;
        System.out.println(maxY);
        return result;
    }
}
