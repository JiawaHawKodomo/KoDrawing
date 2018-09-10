package ui.graph;


import config.Configurations;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2018-09-08 13:25
 */
public abstract class GraphHelper {

    //是否可选中
    private MouseMode mouseMode;
    //是否已选中
    private boolean selected;

    List<Shape> shapes;

    /**
     * 现在在pane上
     *
     * @param pane 所显示的地方
     */
    public void showOn(Pane pane) {
        shapes.forEach(pane.getChildren()::add);
    }

    /**
     * 设置鼠标模式
     */
    public void setMouseMode(MouseMode mouseMode) {
        this.mouseMode = mouseMode;
        if (mouseMode != MouseMode.SELECT) {
            setSelected(false);
        }
    }

    /**
     * 图形上清空选择
     */
    protected void unselectOnShape() {
        shapes.forEach(l -> l.setStrokeWidth(Configurations.getLineOriginalWidth()));
    }

    /**
     * 图形上选择
     */
    protected void selectOnShape() {
        shapes.forEach(l -> l.setStrokeWidth(Configurations.getLineSelectedWidth()));
    }

    protected void initialize() {
        shapes.forEach(l -> {
            l.setStrokeWidth(Configurations.getLineOriginalWidth());
            l.setStrokeLineCap(StrokeLineCap.ROUND);
            l.setStrokeLineJoin(StrokeLineJoin.ROUND);
            l.setOnMouseEntered(new EnterEvent());
            l.setOnMouseExited(new ExitEvent());
            l.setOnMousePressed(new MouseDownEvent());
            l.setOnMouseReleased(new MouseUpEvent());
        });
    }

    /**
     * 获取模式
     *
     * @return boolean
     */
    public MouseMode getMouseMode() {
        return mouseMode;
    }

    /**
     * 设置已选中状态
     *
     * @param isSelected boolean
     */
    public void setSelected(boolean isSelected) {
        selected = isSelected;
        if (!isSelected) {
            unselectOnShape();
        } else {
            selectOnShape();
        }
    }

    /**
     * 查看是否选中
     *
     * @return
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * 获取图形信息
     *
     * @return 图形信息字符串
     */
    public abstract String getInfo();

    /**
     * 鼠标抬起处理
     */
    protected class MouseUpEvent implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if (getMouseMode() == MouseMode.SELECT) {
                setSelected(!isSelected());
            }
        }
    }

    /**
     * 鼠标进入处理
     */
    protected class EnterEvent implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if (getMouseMode() == MouseMode.SELECT) {
                //如果在选择模式下可以选择
                double width = Configurations.getLineEnterWidth();
                shapes.forEach(l -> l.setStrokeWidth(width));
            }
        }
    }


    /**
     * 鼠标退出处理
     */
    protected class ExitEvent implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if (!isSelected()) {
                //在未选中情况下变化
                double width = Configurations.getLineOriginalWidth();
                shapes.forEach(l -> l.setStrokeWidth(width));
            }
        }
    }

    /**
     * 鼠标落下处理
     */
    protected class MouseDownEvent implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if (getMouseMode() == MouseMode.SELECT) {
                double width = Configurations.getLineClickedWidth();
                shapes.forEach(l -> l.setStrokeWidth(width));
            }
        }
    }
}
