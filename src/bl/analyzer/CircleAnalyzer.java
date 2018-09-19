package bl.analyzer;

import bl.model.Point;
import bl.model.graph.Circle;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2018-09-17 15:05
 */
public class CircleAnalyzer extends Analyzer {

    public CircleAnalyzer(List<List<Point>> trace) {
        super(trace);
    }

    @Override
    public void analyze() {
        List<Point> points = pickPointsBy(6);

        if (points.size() < 2) {
            setMatchingRate(Double.MAX_VALUE);
            return;
        }

        SimulatedLine diameter = new SimulatedLine(points.get(0), points.get(1));
        //寻找最远的两点当做直径
        for (Point p1 : points) {
            Point p2 = points.get(1);
            for (Point p : points) {
                if (p1.calculateDistanceToPoint(p) > p1.calculateDistanceToPoint(p2)) {
                    p2 = p;
                }
            }
            if (p1.calculateDistanceToPoint(p2) > diameter.length) {
                diameter = new SimulatedLine(p1, p2);
            }
        }

        //设置图形
        double radius = diameter.length / 2.0;
        Point center = diameter.getMidpoint();
        Circle result = new Circle();
        result.setRadius((int) radius);
        result.setCenter(center);
        setGraph(result);

        //计算匹配度
        setMatchingRate(getPackedUp().parallelStream().mapToDouble(p -> Math.abs(p.calculateDistanceToPoint(center) - radius))
                .average().getAsDouble());
        System.out.println(getMatchingRate());
    }

    @Override
    public Circle getAnalyzedGraph() {
        return (Circle) super.getAnalyzedGraph();
    }
}
