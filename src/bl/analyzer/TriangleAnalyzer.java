package bl.analyzer;

import bl.model.Point;
import bl.model.graph.Triangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Shuaiyu Yao
 * @create 2018-09-17 15:11
 */
public class TriangleAnalyzer extends Analyzer {

    public TriangleAnalyzer(List<List<Point>> trace) {
        super(trace);
    }

    @Override
    public void analyze() {
        List<SimulatedLine> resultLines;
        if (isAllTraceLine() && getTrace().size() == 3) {
            resultLines = getTrace().parallelStream().map(list -> new SimulatedLine(list.get(0), list.get(list.size() - 1)))
                    .sorted((a, b) -> (int) (b.length - a.length))//排序, 从大到小
                    .collect(Collectors.toList());
        } else {
            //以5为单位选取
            List<Point> trace = pickPointsBy(5);

            //构建多边形
            List<Point> simulatedPoints = new LinkedList<>(trace);
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
            resultLines = new ArrayList<>(Arrays.asList(
                    new SimulatedLine(p1, p2),
                    new SimulatedLine(p2, p3),
                    new SimulatedLine(p3, p1)
            ));
        }


        //设置结果
        setGraph(new Triangle(resultLines.get(0).p1, resultLines.get(1).p1, resultLines.get(2).p1));


        //计算匹配度
        setMatchingRate(getPackedUp().parallelStream()
                .mapToDouble(p -> resultLines.parallelStream()
                        .mapToDouble(l -> l.calculateDistanceToPoint(p))
                        .min().getAsDouble())
                .average().getAsDouble());
        System.out.println(getMatchingRate());
    }

    @Override
    public Triangle getAnalyzedGraph() {
        return (Triangle) super.getAnalyzedGraph();
    }
}
