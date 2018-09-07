package ui;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

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

    @FXML
    private void initialize() {
        selectButton.setStyle("-fx-background-image: url('resources/pic/selector.png')");
        drawButton.setStyle("-fx-background-image: url('resources/pic/painter.png')");
        selectButton.setToggleGroup(toolGroup);
        drawButton.setToggleGroup(toolGroup);
        drawButton.setSelected(true);
    }

}
