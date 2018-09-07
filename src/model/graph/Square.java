package model.graph;

import model.Point;

/**
 * 正方形
 *
 * @author Shuaiyu Yao
 * @create 2018-09-07 15:14
 **/
public class Square implements Graph{

    private static final long serialVersionUID = 1L;

    private Point center;//中心点
    private double sideLength;//边长
    private double rotate;//旋转角
    private int id;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
    public Square() {
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public double getSideLength() {
        return sideLength;
    }

    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
    }

    public double getRotate() {
        return rotate;
    }

    public void setRotate(double rotate) {
        this.rotate = rotate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Square)) return false;

        Square square = (Square) o;

        if (Double.compare(square.getSideLength(), getSideLength()) != 0) return false;
        if (Double.compare(square.getRotate(), getRotate()) != 0) return false;
        return getCenter().equals(square.getCenter());
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getCenter().hashCode();
        temp = Double.doubleToLongBits(getSideLength());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getRotate());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
