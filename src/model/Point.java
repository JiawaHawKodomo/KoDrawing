package model;

import java.io.Serializable;

/**
 * @author Shuaiyu Yao
 * @create 2018-09-07 15:10
 */
public class Point implements Serializable {

    private static final long serialVersionUID = 1L;

    private int X;
    private int Y;

    public Point() {
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;

        Point point = (Point) o;

        if (getX() != point.getX()) return false;
        return getY() == point.getY();
    }

    @Override
    public int hashCode() {
        int result = getX();
        result = 31 * result + getY();
        return result;
    }
}
