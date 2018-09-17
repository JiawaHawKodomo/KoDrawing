package bl;

import bl.analyzer.Analyzer;
import bl.analyzer.TriangleAnalyzer;
import bl.model.Point;
import bl.model.graph.Graph;
import bl.model.graph.Triangle;
import ui.MainController;
import ui.graph.GraphHelper;
import ui.graph.TriangleGraphHelper;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2018-09-17 18:07
 */
public class BLService {

    private BLService() {
    }

    private static final BLService blService = new BLService();

    public static BLService getInstance() {
        return blService;
    }

    /**
     * 分析图形
     *
     * @param points
     * @param mainController
     * @return
     */
    public GraphHelper analyze(List<Point> points, MainController mainController) {
        TriangleAnalyzer analyzer = new TriangleAnalyzer(points);
        Triangle triangle = analyzer.getAnalyzedGraph();
        GraphHelper result = new TriangleGraphHelper(mainController, triangle);
        return result;
    }

}
