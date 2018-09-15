package bl;

import javafx.scene.shape.Line;
import model.Point;
import ui.MainController;
import ui.graph.GraphHelper;
import ui.graph.SimpleChangeableGraphHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 绘画过程, 用于分析
 *
 * @author Shuaiyu Yao
 * @create 2018-09-15 12:44
 */
public class TracingProcess {

    private List<Point> currentStroke;
    private List<List<Point>> trace;
    private final String id;

    public TracingProcess() {
        trace = new ArrayList<>();
        id = UUID.randomUUID().toString();
    }

    /**
     * 新笔画
     */
    public void createNewStroke() {
        List<Point> newStroke = new ArrayList<>();
        trace.add(newStroke);
        currentStroke = newStroke;
    }

    /**
     * 新点
     *
     * @param point 新点坐标
     */
    public void addPoint(Point point) {
        currentStroke.add(point);
    }

    /**
     * 获取路径数据
     *
     * @return
     */
    public List<List<Point>> getTrace() {
        return trace;
    }

    /**
     * 返回分析后的图形
     *
     * @return 分析后的模拟图形
     */
    public GraphHelper analyze(MainController mainController) {
        SimpleChangeableGraphHelper result = new SimpleChangeableGraphHelper(mainController);
        //todo
        trace.stream().filter(list -> list != null && list.size() != 0).forEach(list -> {
            Line line = new Line(list.get(0).getX(), list.get(0).getY(), list.get(list.size() - 1).getX(), list.get(list.size() - 1).getY());
            result.addShape(line);
        });
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TracingProcess)) return false;

        TracingProcess that = (TracingProcess) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
