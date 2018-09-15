package ui.graph;


import config.Configurations;
import javafx.scene.paint.Paint;
import model.graph.Circle;
import ui.MainController;

/**
 * 圆形
 *
 * @author Shuaiyu Yao
 * @create 2018-09-10 17:30
 */
public class CircleGraphHelper extends GraphHelper {

    private javafx.scene.shape.Circle circle;

    public CircleGraphHelper(MainController mainController, double radius, int x, int y) {
        super(mainController);
        //绘图
        circle = new javafx.scene.shape.Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(radius);
        circle.setFill(Paint.valueOf("00000000"));//透明
        getShapes().add(circle);

        //属性
        getShapes().forEach(l1 -> l1.setStroke(Paint.valueOf(Configurations.getCirclrColor())));
        initialize();
    }

    public CircleGraphHelper(MainController mainController, Circle circle) {
        this(mainController, circle.getRadius(), circle.getCenter().getX(), circle.getCenter().getY());
    }

    /**
     * 获取图形信息
     *
     * @return 图形信息字符串
     */
    @Override
    public String getInfo() {
        return "圆形:" + System.lineSeparator()
                + "半径:" + String.format("%.2f", circle.getRadius());
    }
}
