package bl;

import bl.analyzer.*;
import bl.model.Point;
import bl.model.graph.*;
import ui.MainController;
import ui.graph.*;

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
    public GraphHelper analyze(List<List<Point>> points, MainController mainController) {
//        TriangleAnalyzer analyzer = new TriangleAnalyzer(points);
//        Triangle triangle = analyzer.getAnalyzedGraph();
//        GraphHelper result = new TriangleGraphHelper(mainController, triangle);
//        return result;

//        RectangleAnalyzer analyzer = new RectangleAnalyzer(points);
//        Rectangle rectangle = analyzer.getAnalyzedGraph();
//        GraphHelper result = new RectangleGraphHelper(mainController, rectangle);
//        return result;

//        SquareAnalyzer analyzer = new SquareAnalyzer(points);
//        Square square = analyzer.getAnalyzedGraph();
//        GraphHelper result = new SquareGraphHelper(mainController, square);
//        return result;

        CircleAnalyzer analyzer = new CircleAnalyzer(points);
        Circle circle = analyzer.getAnalyzedGraph();
        GraphHelper result = new CircleGraphHelper(mainController, circle);
        return result;


    }

}
