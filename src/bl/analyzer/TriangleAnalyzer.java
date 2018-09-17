package bl.analyzer;

import bl.model.Point;
import bl.model.graph.Triangle;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2018-09-17 15:11
 */
public class TriangleAnalyzer extends Analyzer {

    public TriangleAnalyzer(List<Point> trace) {
        super(trace);
    }

    @Override
    public void analyze() {
        //以5为单位选取
        pickPointsBy(5);

        //构建多边形
        List<Point> simulatedPoints = new LinkedList<>(getTrace());
        //初次筛选
        for (int i = 0; i < simulatedPoints.size(); i++) {
            double angle = calculateAngle(simulatedPoints.get(i), simulatedPoints.get((i + 1) % simulatedPoints.size()), simulatedPoints.get((i + 2) % simulatedPoints.size()));
            if (angle > 150.0 / 180.0 * Math.PI) {
                //150度以上的确定删除
                simulatedPoints.remove(i);
                i--;
            }
            if (simulatedPoints.size() == 3) {
                break;
            }
        }

        //寻找3条最长的线
        List<SimulatedLine> lines = new LinkedList<>();
        for (int i = 0; i < simulatedPoints.size(); i++) {
            lines.add(new SimulatedLine(simulatedPoints.get(i), simulatedPoints.get((i + 1) % simulatedPoints.size())));
        }
        lines.sort((a, b) -> (int) (b.length - a.length));
        Point p1 = lines.get(0).calculateIntersection(lines.get(1));
        Point p2 = lines.get(1).calculateIntersection(lines.get(2));
        Point p3 = lines.get(2).calculateIntersection(lines.get(0));
//        do {//循环, 每次将夹角最接近180度的相邻的两条线模拟成一条
//            int currentPointNumber = simulatedPoints.size();
//            List<Double> angles = new ArrayList<>();
//            for (int i = 0; i < currentPointNumber; i++) {
//                //计算所有角
//                angles.add(calculateAngle(simulatedPoints.get(i), simulatedPoints.get((i + 1) % currentPointNumber), simulatedPoints.get((i + 2) % currentPointNumber)));
//
//            }
//        } while (simulatedPoints.size() > 3);//最后剩下三条线

        //设置结果
        setGraph(new Triangle(p1, p2, p3));
    }

    @Override
    public Triangle getAnalyzedGraph() {
        return (Triangle) super.getAnalyzedGraph();
    }
}
