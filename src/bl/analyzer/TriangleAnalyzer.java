package bl.analyzer;

import bl.model.Point;
import bl.model.graph.Triangle;

import java.util.*;
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
        if (getTrace().isEmpty()) {
            setMatchingRate(Double.MAX_VALUE);
            return;
        }

        List<SimulatedLine> resultLines;
        List<Point> resultPoint = new ArrayList<>();
        if (isAllTraceLine() && getTrace().size() == 3) {
            resultLines = getTrace().parallelStream().map(list -> new SimulatedLine(list.get(0), list.get(list.size() - 1)))
                    .sorted((a, b) -> (int) (b.length - a.length))//排序, 从大到小
                    .collect(Collectors.toList());
            resultPoint.add(resultLines.get(0).calculateIntersection(resultLines.get(1)));
            resultPoint.add(resultLines.get(1).calculateIntersection(resultLines.get(2)));
            resultPoint.add(resultLines.get(2).calculateIntersection(resultLines.get(0)));
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
            if (p1 == null || p2 == null || p3 == null) {
                setMatchingRate(Double.MAX_VALUE);//识别失败
                return;
            }
            resultLines = new ArrayList<>(Arrays.asList(
                    new SimulatedLine(p1, p2),
                    new SimulatedLine(p2, p3),
                    new SimulatedLine(p3, p1)
            ));
            resultPoint.addAll(Arrays.asList(p1, p2, p3));
        }


        //设置结果
        setGraph(new Triangle(resultPoint.get(0), resultPoint.get(1), resultPoint.get(2)));


        //计算匹配度
        setMatchingRate(getPackedUp().parallelStream()
                .mapToDouble(p -> resultLines.parallelStream()
                        .mapToDouble(l -> l.calculateDistanceToPoint(p))
                        .min().getAsDouble())
                .average().getAsDouble());
        //如果有特别偏远的判定点, 则判定拟合失败
        if (resultPoint.stream().mapToDouble(s -> getPackedUp().stream().mapToDouble(p -> p.calculateDistanceToPoint(s)).min().getAsDouble()).max().getAsDouble() > 100) {
            setMatchingRate(Double.MAX_VALUE);
        }
        System.out.println("三角形" + getMatchingRate());
    }

    @Override
    public Triangle getAnalyzedGraph() {
        return (Triangle) super.getAnalyzedGraph();
    }
}
