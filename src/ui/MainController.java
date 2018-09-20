package ui;

import bl.BLService;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import ui.graph.TracingProcess;
import config.Configurations;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import bl.model.Point;
import ui.graph.*;

import java.io.File;
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
    private BLService blService;

    private MouseMode mouseMode = MouseMode.NULL;
    private Map<TracingProcess, GraphHelper> map;//键对形式存储笔画路径和模拟图像
    //绘画相关
    private TracingProcess currentTrace;

    @FXML
    private void initialize() {
        blService = BLService.getInstance();
        selectButton.setStyle("-fx-background-image: url('resources/pic/selector.png')");
        drawButton.setStyle("-fx-background-image: url('resources/pic/painter.png')");
        circleButton.setStyle("-fx-background-image: url('resources/pic/circle.png')");
        triangleButton.setStyle("-fx-background-image: url('resources/pic/triangle.png')");
        rectangleButton.setStyle("-fx-background-image: url('resources/pic/rectangle.png')");
        squareButton.setStyle("-fx-background-image: url('resources/pic/square.png')");
        selectButton.setToggleGroup(toolGroup);
        drawButton.setToggleGroup(toolGroup);
        map = new HashMap<>();

        gc = mainCanvas.getGraphicsContext2D();
        enterNewPaintingProcess();//新的图形绘制过程

        drawButton.setUserData(MouseMode.DRAW);
        selectButton.setUserData(MouseMode.SELECT);
        initializeCanvasAndGraphPane();
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

    private void initializeCanvasAndGraphPane() {
        graphGenarationPane.getChildren().clear();
        gc.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        enterNewPaintingProcess();
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

    @FXML
    private void selectCircle() {
        System.out.println("选择了圆形");
        //删除原有图像
        GraphHelper oldGraph = map.get(currentTrace);
        if (oldGraph != null) {
            oldGraph.delete();
        }

        //鼠标抬起, 这一笔画结束, 计算
        GraphHelper newGraph = currentTrace.analyzeToCirlce(this);
        if (newGraph != null) {
            map.put(currentTrace, newGraph);
            newGraph.showOn(graphGenarationPane);
            changeTraceColor(newGraph);
        }
    }

    @FXML
    private void selectTriangle() {
        System.out.println("选择了三角形");
        //删除原有图像
        GraphHelper oldGraph = map.get(currentTrace);
        if (oldGraph != null) {
            oldGraph.delete();
        }

        //鼠标抬起, 这一笔画结束, 计算
        GraphHelper newGraph = currentTrace.analyzeToTriangle(this);
        if (newGraph != null) {
            map.put(currentTrace, newGraph);
            newGraph.showOn(graphGenarationPane);
            changeTraceColor(newGraph);
        }
    }

    @FXML
    private void selectRectangle() {
        System.out.println("选择了长方形");
        //删除原有图像
        GraphHelper oldGraph = map.get(currentTrace);
        if (oldGraph != null) {
            oldGraph.delete();
        }

        //鼠标抬起, 这一笔画结束, 计算
        GraphHelper newGraph = currentTrace.analyzeToRectangle(this);
        if (newGraph != null) {
            map.put(currentTrace, newGraph);
            newGraph.showOn(graphGenarationPane);
            changeTraceColor(newGraph);
        }
    }

    @FXML
    private void selectSquare() {
        System.out.println("选择了正方形");
        //删除原有图像
        GraphHelper oldGraph = map.get(currentTrace);
        if (oldGraph != null) {
            oldGraph.delete();
        }

        //鼠标抬起, 这一笔画结束, 计算
        GraphHelper newGraph = currentTrace.analyzeToSquare(this);
        if (newGraph != null) {
            map.put(currentTrace, newGraph);
            newGraph.showOn(graphGenarationPane);
            changeTraceColor(newGraph);
        }

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

    public void undo() {
        clearLinesOnCanvas(currentTrace.undo());
        analyzeAndShow();
    }

    public void redo() {
        currentTrace.redo().forEach(this::drawAPoint);
        analyzeAndShow();
    }

    /**
     * 进入新的绘画过程
     */
    private void enterNewPaintingProcess() {
        currentTrace = new TracingProcess();
        infoLabel.setText("");
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
     * 改变选择的图形
     */
    public void changeSelectedGraph() {
        for (Map.Entry<TracingProcess, GraphHelper> e : map.entrySet()) {
            if (e.getValue().isSelected()) {
                this.currentTrace = e.getKey();
            }
        }
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
        mainCanvas.setOnMouseReleased(event -> analyzeAndShow());

        //设置界面
        correctButton.setVisible(true);
        deleteButton.setVisible(false);

        //调整画板的位置
        leftPane.getChildren().clear();
        leftPane.getChildren().add(graphGenarationPane);
        leftPane.getChildren().add(mainCanvas);
    }

    private void analyzeAndShow() {
        //删除原有图像
        GraphHelper oldGraph = map.get(currentTrace);
        if (oldGraph != null) {
            oldGraph.delete();
        }

        //鼠标抬起, 这一笔画结束, 计算
        GraphHelper newGraph = currentTrace.analyze(this);
        if (newGraph != null) {
            map.put(currentTrace, newGraph);
            newGraph.showOn(graphGenarationPane);
            changeTraceColor(newGraph);
        }
    }

    private void changeTraceColor(GraphHelper graphHelper) {
        if (graphHelper instanceof CircleGraphHelper) {
            gc.setFill(Paint.valueOf(Configurations.getCirclrColor()));
        } else if (graphHelper instanceof TriangleGraphHelper) {
            gc.setFill(Paint.valueOf(Configurations.getTriangleColor()));
        } else if (graphHelper instanceof RectangleGraphHelper) {
            gc.setFill(Paint.valueOf(Configurations.getRectangleColor()));
        } else if (graphHelper instanceof SquareGraphHelper) {
            gc.setFill(Paint.valueOf(Configurations.getSquareColor()));
        }
        currentTrace.getTrace().forEach(l -> l.forEach(this::drawAPoint));
        gc.setFill(Color.BLACK);
    }

    /********保存读取部分*********/

    @FXML
    private void saveToFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("save to...");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("file", "*.kdm")
        );
        File file = fileChooser.showSaveDialog(mainStage);

        blService.saveToFile(file, map);
    }

    @FXML
    private void openFile() {
        //todo
        
    }

}
