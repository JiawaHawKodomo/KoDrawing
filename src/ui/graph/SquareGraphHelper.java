package ui.graph;

import bl.model.graph.Graph;
import config.Configurations;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import bl.model.graph.Square;
import ui.MainController;

import java.util.Arrays;

/**
 * 正方形图形
 *
 * @author Shuaiyu Yao
 * @create 2018-09-10 11:17
 */
public class SquareGraphHelper extends GraphHelper {

    private Line line1;
    private Line line2;
    private Line line3;
    private Line line4;

    private Square square;

    @Deprecated
    public SquareGraphHelper(MainController mainController, int X, int Y, double r, double rotate) {
        super(mainController);
        //绘制图形
        line1 = new Line();
        line2 = new Line();
        line3 = new Line();
        line4 = new Line();

        double h1 = r * Math.cos(Math.toRadians(45 - rotate)) * Math.pow(2, 0.5) / 2;
        double h2 = r * Math.sin(Math.toRadians(45 - rotate)) * Math.pow(2, 0.5) / 2;

        line1.setStartX(X - h2);
        line1.setStartY(Y - h1);
        line1.setEndX(X + h1);
        line1.setEndY(Y - h2);

        line2.setStartX(X + h1);
        line2.setStartY(Y - h2);
        line2.setEndX(X + h2);
        line2.setEndY(Y + h1);

        line3.setStartX(X + h2);
        line3.setStartY(Y + h1);
        line3.setEndX(X - h1);
        line3.setEndY(Y + h2);

        line4.setStartX(X - h1);
        line4.setStartY(Y + h2);
        line4.setEndX(X - h2);
        line4.setEndY(Y - h1);

        //属性
        getShapes().addAll(Arrays.asList(line1, line2, line3, line4));
        getShapes().forEach(l -> l.setStroke(Paint.valueOf(Configurations.getSquareColor())));
        initialize();
    }

    public SquareGraphHelper(MainController mainController, Square square) {
        this(mainController, square.getCenter().getX(), square.getCenter().getY(), square.getSideLength(), square.getRotate());
        this.square = square;
    }

    /**
     * 获取图形信息
     *
     * @return 图形信息字符串
     */
    @Override
    public String getInfo() {
        return "正方形:" + System.lineSeparator()
                + "边长:" + String.format("%.2f", Math.pow((Math.pow((line1.getStartX() - line1.getEndX()), 2) + Math.pow((line1.getStartY() - line1.getEndY()), 2)), 0.5));
    }

    @Override
    public Graph getGraph() {
        return square;
    }
}
