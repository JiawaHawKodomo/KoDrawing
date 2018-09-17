package ui.graph;

import config.Configurations;
import javafx.scene.paint.Paint;
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
