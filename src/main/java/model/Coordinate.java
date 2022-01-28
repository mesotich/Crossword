package model;

public class Coordinate {

    private final int x, y;
    private char ch;
    private boolean allow;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        this.ch = ' ';
        this.allow = true;
    }

    public Coordinate(int x, int y, char ch, boolean allow) {
        this.x = x;
        this.y = y;
        this.ch = ch;
        this.allow = allow;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getCh() {
        return ch;
    }

    public boolean isAllow() {
        return allow;
    }

    public void setCh(char ch) {
        this.ch = ch;
    }

    public void setAllow(boolean allow) {
        this.allow = allow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        if (x != that.x)
            return false;
        return y == that.y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")(" + ch + "," + allow + ")";
    }
}
