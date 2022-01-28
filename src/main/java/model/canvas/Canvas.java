package model.canvas;

import model.Direction;
import model.Word;

public class Canvas {

    private char[][] matrix;

    public Canvas(int size) {
        this.matrix = new char[size][size];
    }

    public void clear() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = ' ';
            }
        }
    }

    public void setChar(int x, int y, char ch) {
        matrix[y][x] = ch;
    }

    public void setWord(int x0, int y0, Word word) {
        Direction direction = word.getDirection();
        for (int i = 0; i < word.getWord().length(); i++) {
            char ch = word.getChar(i);
            if (direction.equals(Direction.HORIZONTAL)) {
                setChar(x0 + i, y0, ch);
            } else {
                setChar(x0, y0 + i, ch);
            }
        }
    }

    private int deltaBorder(int x0, int y0, Word word) {
        int result;
        if (x0 < 0 || y0 < 0)
            result = Math.min(x0, y0);
        if (word.getDirection().equals(Direction.HORIZONTAL))
            result = Math.max(x0 + word.getWord().length() - 1, y0) - size() - 1;
        else result = Math.max(x0, y0 + word.getWord().length() - 1) - size() - 1;
        return result;
    }

    public int size() {
        return matrix.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                sb.append(matrix[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
