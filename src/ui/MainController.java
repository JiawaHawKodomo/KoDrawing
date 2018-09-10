package ui;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Point;
import ui.graph.*;

import java.util.HashSet;
import java.util.Set;

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

    final ToggleGroup toolGroup = new ToggleGroup();
    private Stage mainStage;

    private MouseMode mouseMode = MouseMode.NULL;

    private Set<GraphHelper> graphSet;

    @FXML
    private void initialize() {
        selectButton.setStyle("-fx-background-image: url('resources/pic/selector.png')");
        drawButton.setStyle("-fx-background-image: url('resources/pic/painter.png')");
        selectButton.setToggleGroup(toolGroup);
        drawButton.setToggleGroup(toolGroup);
        drawButton.setSelected(true);
        graphSet = new HashSet<>();


        Point p1 = new Point();
        p1.setX(100);
        p1.setY(100);
        Point p2 = new Point();
        p2.setX(100);
        p2.setY(10);
        Point p3 = new Point();
        p3.setX(50);
        p3.setY(50);

        TriangleGraphHelper tg = new TriangleGraphHelper(p1, p2, p3);
        tg.showOn(graphGenarationPane);
        graphSet.add(tg);

        SquareGraphHelper sg = new SquareGraphHelper(300, 300, 50, 135);
        sg.showOn(graphGenarationPane);
        graphSet.add(sg);

        RectangleGraphHelper rg = new RectangleGraphHelper(100, 300, 70, 50, 170);
        rg.showOn(graphGenarationPane);
        graphSet.add(rg);

        CircleGraphHelper cg = new CircleGraphHelper(50, 300, 100);
        cg.showOn(graphGenarationPane);
        graphSet.add(cg);
        System.out.println(cg.getInfo());

        drawButton.setUserData(MouseMode.DRAW);
        selectButton.setUserData(MouseMode.SELECT);
        //模式选择
        toolGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) -> {
            if (new_toggle == null) {
                mouseMode = MouseMode.NULL;
            } else {
                MouseMode newMode = (MouseMode) toolGroup.getSelectedToggle().getUserData();
                mouseMode = newMode;
                graphSet.forEach(g -> g.setMouseMode(newMode));
            }
        });
    }

    public void setStage(Stage stage) {
        mainStage = stage;
    }

    @FXML
    private void modeButtonEvent() {
    }

}
