package ui.graph;

import config.Configurations;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import model.graph.Rectangle;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * 长方形
 *
 * @author Shuaiyu Yao
 * @create 2018-09-10 16:26
 */
public class RectangleGraphHelper extends GraphHelper {

    private Line line1;
    private Line line2;
    private Line line3;
    private Line line4;

    public RectangleGraphHelper(int x, int y, double l, double w, double rotate) {
        super();
        //图形
        line1 = new Line();
        line2 = new Line();
        line3 = new Line();
        line4 = new Line();
        getShapes().addAll(Arrays.asList(line1, line2, line3, line4));

        double r = Math.pow((w * w + l * l), 0.5) / 2;
        double alpha = Math.atan(w / l);
        double h1 = r * Math.cos(Math.PI / 2 - alpha - Math.toRadians(rotate));
        double h2 = r * Math.sin(Math.PI / 2 - alpha - Math.toRadians(rotate));
        double h3 = r * Math.cos(Math.toRadians(rotate) - alpha);
        double h4 = r * Math.sin(Math.toRadians(rotate) - alpha);

        line1.setStartX(x - h2);
        line1.setStartY(y - h1);
        line1.setEndX(x + h3);
        line1.setEndY(y + h4);

        line2.setStartX(x + h3);
        line2.setStartY(y + h4);
        line2.setEndX(x + h2);
        line2.setEndY(y + h1);

        line3.setStartX(x + h2);
        line3.setStartY(y + h1);
        line3.setEndX(x - h3);
        line3.setEndY(y - h4);

        line4.setStartX(x - h3);
        line4.setStartY(y - h4);
        line4.setEndX(x - h2);
        line4.setEndY(y - h1);

        //属性
        getShapes().forEach(l1 -> l1.setStroke(Paint.valueOf(Configurations.getRectangleColor())));
        initialize();
    }

    public RectangleGraphHelper(Rectangle rectangle) {
        this(rectangle.getCenter().getX(), rectangle.getCenter().getY(), rectangle.getLength(), rectangle.getWidth(), rectangle.getRotate());
    }


    /**
     * 获取图形信息
     *
     * @return 图形信息字符串
     */
    @Override
    public String getInfo() {
        return "长方形" + System.lineSeparator()
                + "长:" + String.format("%.2f", Math.pow((Math.pow((line1.getStartX() - line1.getEndX()), 2) + Math.pow((line1.getStartY() - line1.getEndY()), 2)), 0.5)) + System.lineSeparator()
                + "宽:" + String.format("%.2f", Math.pow((Math.pow((line2.getStartX() - line2.getEndX()), 2) + Math.pow((line2.getStartY() - line2.getEndY()), 2)), 0.5));
    }
}
