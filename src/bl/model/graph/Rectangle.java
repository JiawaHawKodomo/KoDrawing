package bl.model.graph;

import bl.model.Point;

/**
 * 长方形
 *
 * @author Shuaiyu Yao
 * @create 2018-09-07 15:19
 */
public class Rectangle implements Graph {

    private static final long serialVersionUID = 1L;

    private int length;//长
    private int width;//宽
    private Point center;//中心点
    private double rotate;// 旋转角

    public Rectangle() {
    }
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public double getRotate() {
        return rotate;
    }

    public void setRotate(double rotate) {
        this.rotate = rotate;
    }
}
