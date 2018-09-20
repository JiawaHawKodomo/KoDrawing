package bl;

import bl.analyzer.*;
import bl.model.Point;
import bl.model.graph.Circle;
import bl.model.graph.Rectangle;
import bl.model.graph.Square;
import bl.model.graph.Triangle;
import ui.MainController;
import ui.graph.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        List<Analyzer> analyzers = new ArrayList<>();
        Analyzer bestAnalyzer;

        //如果只有1笔, 先考虑圆形
        if (points.size() == 1) {
            CircleAnalyzer analyzer = new CircleAnalyzer(points);
            if (analyzer.getMatchingRate() <= 5.5) {
                return new CircleGraphHelper(mainController, analyzer.getAnalyzedGraph());
            }
        }
        //如果3笔, 先考虑三角
        if (points.size() == 3) {
            TriangleAnalyzer analyzer = new TriangleAnalyzer(points);
            if (analyzer.getMatchingRate() <= 5) {
                return new TriangleGraphHelper(mainController, analyzer.getAnalyzedGraph());
            }
        }
        //如果4笔, 先考虑正方形长方形
        if (points.size() == 4) {
            SquareAnalyzer squareAnalyzer = new SquareAnalyzer(points);
            if (squareAnalyzer.getMatchingRate() <= 5) {
                return new SquareGraphHelper(mainController, squareAnalyzer.getAnalyzedGraph());
            }

            RectangleAnalyzer rectangleAnalyzer = new RectangleAnalyzer(points);
            if (rectangleAnalyzer.getMatchingRate() <= 5) {
                return new RectangleGraphHelper(mainController, rectangleAnalyzer.getAnalyzedGraph());
            }
        }

        CircleAnalyzer circleAnalyzer = new CircleAnalyzer(points);
        TriangleAnalyzer triangleAnalyzer = new TriangleAnalyzer(points);
        RectangleAnalyzer rectangleAnalyzer = new RectangleAnalyzer(points);
        SquareAnalyzer squareAnalyzer = new SquareAnalyzer(points);
        bestAnalyzer = circleAnalyzer;
        analyzers.add(triangleAnalyzer);
        analyzers.add(rectangleAnalyzer);
        analyzers.add(squareAnalyzer);
        for (Analyzer a : analyzers) {
            if (a.getMatchingRate() < bestAnalyzer.getMatchingRate()) {
                bestAnalyzer = a;
            }
        }
        if (bestAnalyzer.getMatchingRate() <= 5) {
            if (bestAnalyzer instanceof CircleAnalyzer)
                return new CircleGraphHelper(mainController, ((CircleAnalyzer) bestAnalyzer).getAnalyzedGraph());
            else if (bestAnalyzer instanceof TriangleAnalyzer)
                return new TriangleGraphHelper(mainController, ((TriangleAnalyzer) bestAnalyzer).getAnalyzedGraph());
            else if (bestAnalyzer instanceof RectangleAnalyzer)
                return new RectangleGraphHelper(mainController, ((RectangleAnalyzer) bestAnalyzer).getAnalyzedGraph());
            else if (bestAnalyzer instanceof SquareAnalyzer)
                return new SquareGraphHelper(mainController, ((SquareAnalyzer) bestAnalyzer).getAnalyzedGraph());
        }
        return new SimpleChangeableGraphHelper(mainController, new SimpleGraphAnalyzer(points).getAnalyzedGraph());
    }


    public CircleGraphHelper analyzeToCirlce(List<List<Point>> points, MainController mainController) {
        CircleAnalyzer analyzer = new CircleAnalyzer(points);
        Circle circle = analyzer.getAnalyzedGraph();
        if (circle != null)
            return new CircleGraphHelper(mainController, circle);
        else
            return null;

    }

    public TriangleGraphHelper analyzeToTriangle(List<List<Point>> points, MainController mainController) {
        TriangleAnalyzer analyzer = new TriangleAnalyzer(points);
        Triangle triangle = analyzer.getAnalyzedGraph();
        if (triangle != null)
            return new TriangleGraphHelper(mainController, triangle);
        else
            return null;
    }

    public RectangleGraphHelper analyzeToRectangle(List<List<Point>> points, MainController mainController) {
        RectangleAnalyzer analyzer = new RectangleAnalyzer(points);
        Rectangle rectangle = analyzer.getAnalyzedGraph();
        if (rectangle != null)
            return new RectangleGraphHelper(mainController, rectangle);
        else
            return null;
    }

    public SquareGraphHelper analyzeToSquare(List<List<Point>> points, MainController mainController) {
        SquareAnalyzer analyzer = new SquareAnalyzer(points);
        Square square = analyzer.getAnalyzedGraph();
        if (square != null)
            return new SquareGraphHelper(mainController, square);
        else
            return null;
    }

    public void saveToFile(File file, Map<TracingProcess, GraphHelper> map) {

    }
}
