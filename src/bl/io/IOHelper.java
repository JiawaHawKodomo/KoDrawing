package bl.io;

import bl.io.io_model.PictureScene;
import bl.io.io_model.RealTrace;
import bl.model.graph.Graph;
import ui.MainController;
import ui.graph.GraphHelper;
import ui.graph.GraphHelperFactory;
import ui.graph.TracingProcess;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Shuaiyu Yao
 * @create 2018-09-20 19:55
 */
public class IOHelper {

    private IOHelper() {
    }

    private static final IOHelper ioHelper = new IOHelper();

    public static IOHelper getInstance() {
        return ioHelper;
    }


    /**
     * 转换为ui对象
     *
     * @param mainController mainController
     * @param pictureScene   持久化类
     * @return
     */
    public Map<TracingProcess, GraphHelper> swapToUI(MainController mainController, PictureScene pictureScene) {
        return pictureScene.getMap().entrySet().stream().collect(Collectors.toMap(e -> {
            TracingProcess tracingProcess = new TracingProcess();
            tracingProcess.setTrace(e.getKey().getPoints());
            return tracingProcess;
        }, e -> GraphHelperFactory.newGraphHelper(mainController, e.getValue())));
    }

    /**
     * 转换成为持久化类
     *
     * @param map map
     * @return 持久化类
     */
    public PictureScene swapToSerilizationClass(Map<TracingProcess, GraphHelper> map) {
        PictureScene pictureScene = new PictureScene();
        Map<RealTrace, Graph> resultMap = map.entrySet().stream().collect(Collectors.toMap(e -> {
            RealTrace realTrace = new RealTrace();
            realTrace.setPoints(e.getKey().getTrace());
            return realTrace;
        }, e -> e.getValue().getGraph()));
        pictureScene.setMap(resultMap);
        return pictureScene;
    }
}
