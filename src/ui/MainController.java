package ui;

import ui.graph.TracingProcess;
import config.Configurations;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import bl.model.Point;
import ui.graph.*;

import java.util.*;

public class MainController {

    @FXML
    private ToggleButton selectButton;//选择模式按钮
    @FXML
    private ToggleButton drawButton;//绘画模式按钮
    @FXML
    private Button triangleButton;//标三角形
    @FXML
    private Button rectangleButton;//标长方形
    @FXML
    private Button squareButton;//标正方形
    @FXML
    private Button circleButton;//标圆形
    @FXML
    private Canvas mainCanvas;//主画板
    @FXML
    private AnchorPane graphGenarationPane;//图形生成板
    @FXML
    private AnchorPane leftPane;
    @FXML
    private Label infoLabel;//信息标签
    @FXML
    private RadioButton generatedGraphVisibleRadioButton;
    @FXML
    private RadioButton realTraceVisibleRadioButton;
    @FXML
    private Button correctButton;
    @FXML
    private Button deleteButton;

    final ToggleGroup toolGroup = new ToggleGroup();
    private Stage mainStage;
    private GraphicsContext gc;

    private MouseMode mouseMode = MouseMode.NULL;
    private Map<TracingProcess, GraphHelper> map;//键对形式存储笔画路径和模拟图像
    //绘画相关
    private TracingProcess currentTrace;

    @FXML
    private void initialize() {
        selectButton.setStyle("-fx-background-image: url('resources/pic/selector.png')");
        drawButton.setStyle("-fx-background-image: url('resources/pic/painter.png')");
        selectButton.setToggleGroup(toolGroup);
        drawButton.setToggleGroup(toolGroup);
        map = new HashMap<>();

        gc = mainCanvas.getGraphicsContext2D();
        enterNewPaintingProcess();//新的图形绘制过程


        /*test*/
        Point p1 = new Point();
        p1.setX(100);
        p1.setY(100);
        Point p2 = new Point();
        p2.setX(100);
        p2.setY(10);
        Point p3 = new Point();
        p3.setX(50);
        p3.setY(50);

        TriangleGraphHelper tg = new TriangleGraphHelper(this, p1, p2, p3);
        tg.showOn(graphGenarationPane);
        map.put(new TracingProcess(), tg);

        SquareGraphHelper sg = new SquareGraphHelper(this, 300, 300, 50, 135);
        sg.showOn(graphGenarationPane);
        map.put(new TracingProcess(), sg);

        RectangleGraphHelper rg = new RectangleGraphHelper(this, 100, 300, 70, 50, 170);
        rg.showOn(graphGenarationPane);
        map.put(new TracingProcess(), rg);

        CircleGraphHelper cg = new CircleGraphHelper(this, 50, 300, 100);
        cg.showOn(graphGenarationPane);
        map.put(new TracingProcess(), cg);

        SimpleChangeableGraphHelper scg = new SimpleChangeableGraphHelper(this);
        Line line1 = new Line(10, 10, 40, 40);
        graphGenarationPane.getChildren().add(line1);
        Line line2 = new Line(40, 40, 80, 40);
        graphGenarationPane.getChildren().add(line2);
        scg.addShape(line1);
        scg.addShape(line2);
        map.put(new TracingProcess(), scg);
        System.out.println(scg.getInfo());
        /*test*/

        drawButton.setUserData(MouseMode.DRAW);
        selectButton.setUserData(MouseMode.SELECT);

        //模式选择
        toolGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) -> {
            if (new_toggle == null) {
                mouseMode = MouseMode.NULL;
            } else {
                MouseMode newMode = (MouseMode) toolGroup.getSelectedToggle().getUserData();
                mouseMode = newMode;
                map.forEach((k, v) -> v.setMouseMode(newMode));
                switch (mouseMode) {//改变模式逻辑
                    case SELECT:
                        toSelectMode();
                        break;
                    case DRAW:
                        toDrawMode();
                        break;
                }
            }
        });
    }

    @FXML
    private void setGeneratedGraphVisible() {
        map.forEach((k, v) -> v.setVisible(generatedGraphVisibleRadioButton.isSelected()));
    }

    @FXML
    private void setRealTraceVisible() {
        mainCanvas.setVisible(realTraceVisibleRadioButton.isSelected());
    }

    /**
     * 删除某个图形
     */
    @FXML
    private void deleteGraph() {
        TracingProcess tracingProcess = searchForSelectedGraph();
        if (tracingProcess != null) {
            GraphHelper graph = map.get(tracingProcess);
            map.remove(tracingProcess);//从map中移除
            graph.delete();//删除模拟图形
            clearTrace(tracingProcess.getTrace());//擦除笔迹

            if (tracingProcess == currentTrace) {//当前正在画的图案
                enterNewPaintingProcess();
            }
        }
    }

    /**
     * 某个图形识别完毕
     */
    @FXML
    private void correctGraph() {
        enterNewPaintingProcess();
    }

    /**
     * 寻找选择中的图形
     *
     * @return 选择中的图形, TracingProcess, 如果没有选中的返回null
     */
    private TracingProcess searchForSelectedGraph() {
        for (Map.Entry<TracingProcess, GraphHelper> e : map.entrySet()) {
            if (e.getValue().isSelected()) {
                return e.getKey();
            }
        }
        return null;
    }

    public void setStage(Stage stage) {
        mainStage = stage;
    }

    /**
     * 清除选择
     */
    public void clearSelect() {
        map.forEach((k, v) -> v.setSelected(false));
    }

    public void setShapeInfo(String info) {
        this.infoLabel.setText(info);
    }

    /**
     * 进入新的绘画过程
     */
    private void enterNewPaintingProcess() {
        currentTrace = new TracingProcess();
    }

    /**
     * 画一个点
     *
     * @param point point
     */
    private void drawAPoint(Point point) {
        gc.save();
        gc.fillOval(point.getX(), point.getY(), Configurations.getThickness(), Configurations.getThickness());
        gc.restore();
    }

    /**
     * 擦除一条线
     *
     * @param list 点的列表
     */
    private void clearLinesOnCanvas(List<Point> list) {
        list.forEach(p -> gc.clearRect(p.getX(), p.getY(), Configurations.getThickness(), Configurations.getThickness()));
    }

    /**
     * 擦除一个图形轨迹
     *
     * @param list list
     */
    private void clearTrace(List<List<Point>> list) {
        list.forEach(this::clearLinesOnCanvas);
    }

    /**
     * 转为选择模式
     */
    private void toSelectMode() {
        //删除画布鼠标事件
        mainCanvas.setOnMousePressed(event -> {
        });
        mainCanvas.setOnMouseDragged(event -> {
        });
        mainCanvas.setOnMouseReleased(event -> {
        });

        //设置界面
        correctButton.setVisible(false);
        deleteButton.setVisible(true);

        //调整画板的位置
        leftPane.getChildren().clear();
        leftPane.getChildren().add(mainCanvas);
        leftPane.getChildren().add(graphGenarationPane);

    }

    /**
     * 转为绘画模式
     */
    private void toDrawMode() {
        //建立画布事件
        mainCanvas.setOnMousePressed(event -> {
            //鼠标按下准备记录
            currentTrace.createNewStroke();
            Point point = new Point((int) event.getX(), (int) event.getY());
            currentTrace.addPoint(point);
            drawAPoint(point);
        });
        mainCanvas.setOnMouseDragged(event -> {
            //鼠标拖动事件,画点
            Point point = new Point((int) event.getX(), (int) event.getY());
            currentTrace.addPoint(point);
            drawAPoint(point);
        });
        mainCanvas.setOnMouseReleased(event -> {
            //删除原有图像
            GraphHelper oldGraph = map.get(currentTrace);
            if (oldGraph != null) {
                oldGraph.delete();
            }

            //鼠标抬起, 这一笔画结束, 计算
            GraphHelper newGraph = currentTrace.analyze(this);
            map.put(currentTrace, newGraph);
            newGraph.showOn(graphGenarationPane);

        });

        //设置界面
        correctButton.setVisible(true);
        deleteButton.setVisible(false);

        //调整画板的位置
        leftPane.getChildren().clear();
        leftPane.getChildren().add(graphGenarationPane);
        leftPane.getChildren().add(mainCanvas);
    }
}
