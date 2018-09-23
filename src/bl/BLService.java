package bl;

import bl.analyzer.*;
import bl.io.IOHelper;
import bl.io.io_model.PictureScene;
import bl.model.Point;
import bl.model.graph.Circle;
import bl.model.graph.Rectangle;
import bl.model.graph.Square;
import bl.model.graph.Triangle;
import ui.MainController;
import ui.graph.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Shuaiyu Yao
 * @create 2018-09-17 18:07
 */
public class BLService {

    private IOHelper ioHelper;

    private BLService() {
        ioHelper = IOHelper.getInstance();
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

    /**
     * 保存对象到文件
     *
     * @param file file
     * @param map  要存的类
     */
    public void saveToFile(File file, Map<TracingProcess, GraphHelper> map) {
        PictureScene pictureScene = ioHelper.swapToSerilizationClass(map);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(pictureScene);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件
     *
     * @param mainController mainController
     * @param file           选择的文件
     * @return 读取到的ui类
     * @throws IOException            文件出错
     * @throws ClassNotFoundException 无法读取
     */
    public Map<TracingProcess, GraphHelper> readFile(MainController mainController, File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            PictureScene pictureScene = (PictureScene) ois.readObject();
            return ioHelper.swapToUI(mainController, pictureScene);
        }
    }
}
