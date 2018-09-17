package ui.graph;

import config.Configurations;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import bl.model.Point;
import bl.model.graph.Triangle;
import ui.MainController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Shuaiyu Yao
 * @create 2018-09-08 10:23
 */
public class TriangleGraphHelper extends GraphHelper {

    private Line line1;
    private Line line2;
    private Line line3;

    public TriangleGraphHelper(MainController mainController, Point p1, Point p2, Point p3) {
        super(mainController);
        //绘制图形
        line1 = new Line();
        line2 = new Line();
        line3 = new Line();
        getShapes().addAll(Arrays.asList(line1, line2, line3));

        line1.setStartX(p1.getX());
        line1.setStartY(p1.getY());
        line1.setEndX(p2.getX());
        line1.setEndY(p2.getY());

        line2.setStartX(p2.getX());
        line2.setStartY(p2.getY());
        line2.setEndX(p3.getX());
        line2.setEndY(p3.getY());

        line3.setStartX(p3.getX());
        line3.setStartY(p3.getY());
        line3.setEndX(p1.getX());
        line3.setEndY(p1.getY());

        //属性
        getShapes().forEach(l -> l.setStroke(Paint.valueOf(Configurations.getTriangleColor())));
        initialize();
    }

    public TriangleGraphHelper(MainController mainController, Triangle triangle) {
        this(mainController, triangle.getPoint1(), triangle.getPoint2(), triangle.getPoint3());
    }


    /**
     * 获取图形信息
     *
     * @return 图形信息字符串
     */
    @Override
    public String getInfo() {
        List<Double> lengths = getSideLength();
        return "三角形:" + System.lineSeparator() + "边长1:" + String.format("%.2f", lengths.get(0))
                + System.lineSeparator() + "边长2:" + String.format("%.2f", lengths.get(1))
                + System.lineSeparator() + "边长3:" + String.format("%.2f", lengths.get(2));
    }

    /**
     * 计算三边边长
     *
     * @return 长度为3的数组, 3边边长
     */
    private List<Double> getSideLength() {
        return getShapes().stream().map(l -> Math.pow((Math.pow((((Line) l).getStartX() - ((Line) l).getEndX()), 2) + Math.pow((((Line) l).getStartY() - ((Line) l).getEndY()), 2)), 0.5)).collect(Collectors.toList());
    }
}
