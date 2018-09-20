package ui.graph;

import bl.model.graph.SimpleGraph;
import config.Configurations;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import ui.MainController;

/**
 * @author Shuaiyu Yao
 * @create 2018-09-13 16:47
 */
public class SimpleChangeableGraphHelper extends GraphHelper implements Changeable {

    public SimpleChangeableGraphHelper(MainController mainController) {
        super(mainController);
    }

    public SimpleChangeableGraphHelper(MainController mainController, SimpleGraph simpleGraph) {
        super(mainController);
        if (simpleGraph != null) {
            simpleGraph.getLines().forEach(l -> addShape(new Line(l.getP1().getX(), l.getP1().getY(), l.getP2().getX(), l.getP2().getY())));
        }
    }

    /**
     * 添加线
     *
     * @param shape shape
     */
    @Override
    public void addShape(Shape shape) {
        getShapes().add(shape);
        initializeImpl(shape);
        getShapes().forEach(l1 -> l1.setStroke(Paint.valueOf(Configurations.getSimpleColor())));
    }

    /**
     * 获取图形信息
     *
     * @return 图形信息字符串
     */
    @Override
    public String getInfo() {
        return "非规整图形:" + System.lineSeparator()
                + "线数量:" + getShapes().size();
    }
}
