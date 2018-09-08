package ui.graph;

import config.Configurations;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import model.Point;
import model.graph.Triangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author Shuaiyu Yao
 * @create 2018-09-08 10:23
 */
public class TriangleGraphHelper extends GraphHelper {

    private Line line1;
    private Line line2;
    private Line line3;

    private List<Line> lines;


    public TriangleGraphHelper(Point p1, Point p2, Point p3) {
        line1 = new Line();
        line2 = new Line();
        line3 = new Line();
        lines = new ArrayList<>(Arrays.asList(line1, line2, line3));
        setMouseMode(MouseMode.NULL);
        setSelected(false);

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

        lines.forEach(l -> {
            l.setStroke(Paint.valueOf("07c92a"));
            l.setStrokeWidth(Configurations.getLineOriginalWidth());
            l.setStrokeLineCap(StrokeLineCap.ROUND);
            l.setStrokeLineJoin(StrokeLineJoin.ROUND);
            l.setOnMouseEntered(new EnterEvent());
            l.setOnMouseExited(new ExitEvent());
            l.setOnMousePressed(new MouseDownEvent());
            l.setOnMouseReleased(new MouseUpEvent());
        });
    }

    public TriangleGraphHelper(Triangle triangle) {
        this(triangle.getPoint1(), triangle.getPoint2(), triangle.getPoint3());
    }

    /**
     * 鼠标进入处理
     */
    private class EnterEvent implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if (getMouseMode() == MouseMode.SELECT) {
                //如果在选择模式下可以选择
                double width = Configurations.getLineEnterWidth();
                lines.forEach(l -> l.setStrokeWidth(width));
            }
        }
    }

    /**
     * 鼠标退出处理
     */
    private class ExitEvent implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if (!isSelected()) {
                //在未选中情况下变化
                double width = Configurations.getLineOriginalWidth();
                lines.forEach(l -> l.setStrokeWidth(width));
            }
        }
    }

    /**
     * 鼠标落下处理
     */
    private class MouseDownEvent implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if (getMouseMode() == MouseMode.SELECT) {
                double width = Configurations.getLineClickedWidth();
                lines.forEach(l -> l.setStrokeWidth(width));
            }
        }
    }

    /**
     * 鼠标抬起处理
     */
    private class MouseUpEvent implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if (getMouseMode() == MouseMode.SELECT) {
                if (isSelected()) {
                    unselect();
                } else {
                    setSelected(true);
                    lines.forEach(l -> l.setStrokeWidth(Configurations.getLineSelectedWidth()));
                }
            }
        }
    }

    @Override
    public void showOn(Pane pane) {
        lines.forEach(l -> pane.getChildren().add(l));
    }

    /**
     * 设置已选中状态
     *
     * @param isSelected
     */
    @Override
    //todo
    public void setSelected(boolean isSelected) {
        selected = isSelected;
    }

    /**
     * 清空选择
     */
    @Override
    public void unselect() {
        setSelected(false);
        lines.forEach(l -> l.setStrokeWidth(Configurations.getLineOriginalWidth()));
    }
}
