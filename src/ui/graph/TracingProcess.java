package ui.graph;

import bl.BLService;
import javafx.scene.shape.Line;
import bl.model.Point;
import ui.MainController;

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
    private List<List<Point>> revoked;
    private final String id;
    private final BLService blService = BLService.getInstance();

    public TracingProcess() {
        trace = new ArrayList<>();
        revoked = new ArrayList<>();
        id = UUID.randomUUID().toString();
    }

    /**
     * 新笔画
     */
    public void createNewStroke() {
        List<Point> newStroke = new ArrayList<>();
        trace.add(newStroke);
        currentStroke = newStroke;
        //清除撤销区
        revoked.clear();
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
     * 撤销上一步操作
     */
    public List<Point> undo() {
        if (trace.size() != 0) {
            List<Point> result = trace.get(trace.size() - 1);
            System.out.println(result);
            revoked.add(result);
            trace.remove(trace.size() - 1);
            return result;
        }
        return new ArrayList<>();
    }

    /**
     * 重做
     */
    public List<Point> redo() {
        if (revoked.size() != 0) {
            List<Point> result = revoked.get(revoked.size() - 1);
            trace.add(result);
            revoked.remove(result);
            return result;
        }
        return new ArrayList<>();
    }

    /**
     * 返回分析后的图形
     *
     * @return 分析后的模拟图形
     */
    public GraphHelper analyze(MainController mainController) {
        return blService.analyze(trace, mainController);
    }

    public CircleGraphHelper analyzeToCirlce(MainController mainController) {
        return blService.analyzeToCirlce(trace, mainController);
    }

    public TriangleGraphHelper analyzeToTriangle(MainController mainController) {
        return blService.analyzeToTriangle(trace, mainController);
    }

    public RectangleGraphHelper analyzeToRectangle(MainController mainController) {
        return blService.analyzeToRectangle(trace, mainController);
    }

    public SquareGraphHelper analyzeToSquare(MainController mainController) {
        return blService.analyzeToSquare(trace, mainController);
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
