package bl.model.graph;

import bl.model.Point;

/**
 * 圆
 *
 * @author Shuaiyu Yao
 * @create 2018-09-07 15:09
 */
public class Circle implements Graph{

    private static final long serialVersionUID = 1L;

    private Point center;
    private int radius;

    public Circle() {
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Circle)) return false;

        Circle circle = (Circle) o;

        if (getRadius() != circle.getRadius()) return false;
        return getCenter().equals(circle.getCenter());
    }

    @Override
    public int hashCode() {
        int result = getCenter().hashCode();
        result = 31 * result + getRadius();
        return result;
    }
}
