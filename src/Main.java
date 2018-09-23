import config.Configurations;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.MainController;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //加载配置文件
        Configurations.loadProperties();
        //初始化界面
        initUI(primaryStage);
    }

    //初始化界面
    private void initUI(Stage primaryStage) {
        try {
            // loader
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("Main1.fxml"));

            // stage
            primaryStage.setTitle("KoDrawing");

            // scene
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            scene.getStylesheets().add(
                    MainController.class.getResource("MainStyle.css")
                            .toExternalForm());

            // controller
            MainController controller = loader.getController();
            controller.setStage(primaryStage);

            // show
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
