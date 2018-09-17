package bl.model.graph;

import bl.model.Point;

/**
 * 三角形
 *
 * @author Shuaiyu Yao
 * @create 2018-09-07 15:12
 */
public class Triangle implements Graph {

    private static final long serialVersionUID = 1L;

    private Point point1;
    private Point point2;
    private Point point3;


    public Triangle() {
    }

    public Triangle(Point p1, Point p2, Point p3) {
        point1 = p1;
        point2 = p2;
        point3 = p3;
    }

    public Point getPoint1() {
        return point1;
    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    public Point getPoint3() {
        return point3;
    }

    public void setPoint3(Point point3) {
        this.point3 = point3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Triangle)) return false;

        Triangle triangle = (Triangle) o;

        if (!getPoint1().equals(triangle.getPoint1())) return false;
        if (!getPoint2().equals(triangle.getPoint2())) return false;
        return getPoint3().equals(triangle.getPoint3());
    }

    @Override
    public int hashCode() {
        int result = getPoint1().hashCode();
        result = 31 * result + getPoint2().hashCode();
        result = 31 * result + getPoint3().hashCode();
        return result;
    }
}
