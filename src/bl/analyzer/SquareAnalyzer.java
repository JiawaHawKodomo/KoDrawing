package bl.analyzer;

import bl.model.Point;
import bl.model.graph.Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Shuaiyu Yao
 * @create 2018-09-17 15:06
 */
public class SquareAnalyzer extends Analyzer {

    public SquareAnalyzer(List<List<Point>> trace) {
        super(trace);
    }

    @Override
    public void analyze() {
        if (getTrace().isEmpty()) {
            setMatchingRate(Double.MAX_VALUE);
            return;
        }

        List<SimulatedLine> resultLines;
        //首先判断trace的每个子列表是否是一条直线
        if (isAllTraceLine() && getTrace().size() >= 4) {//如果判断全是直线
            resultLines = getTrace().parallelStream().map(list -> new SimulatedLine(list.get(0), list.get(list.size() - 1)))
                    .collect(Collectors.toList())
                    .subList(0, 4);
        } else {//如果不是,按照传统方法处理
            //以2为单位选取
            List<Point> trace = pickPointsBy(3);

            //构建多边形
            List<Point> simulatedPoints = new LinkedList<>(trace);
            //初次筛选
            for (int i = 0; i < simulatedPoints.size(); i++) {
                double angle = calculateAngle(simulatedPoints.get(i), simulatedPoints.get((i + 1) % simulatedPoints.size()), simulatedPoints.get((i + 2) % simulatedPoints.size()));
                if (angle > 135 / 180.0 * Math.PI) {
                    //135度以上的确定删除
                    simulatedPoints.remove(i);
                    i--;
                }
                if (simulatedPoints.size() == 4) {
                    break;
                }
            }

            //寻找4条最长的线
            List<SimulatedLine> lines = new LinkedList<>();
            for (int i = 0; i < simulatedPoints.size(); i++) {
                lines.add(new SimulatedLine(simulatedPoints.get(i), simulatedPoints.get((i + 1) % simulatedPoints.size())));
            }
            lines.sort((a, b) -> (int) (b.length - a.length));
            resultLines = lines.subList(0, 4);//01为长, 23位宽
        }

        //设置结果
        int sideLength = (int) resultLines.parallelStream().mapToDouble(l -> (int) l.length).average().getAsDouble();
        Point center = new SimulatedLine(new SimulatedLine(resultLines.get(0).getMidpoint(), resultLines.get(1).getMidpoint()).getMidpoint(),
                new SimulatedLine(resultLines.get(2).getMidpoint(), resultLines.get(3).getMidpoint()).getMidpoint()).getMidpoint();
        double rotate = -Math.toDegrees(Math.atan(-resultLines.get(0).slope));
        Square result = new Square();
        result.setSideLength(sideLength);
        result.setCenter(center);
        result.setRotate(rotate);
        setGraph(result);

        //计算匹配度
        setMatchingRate(getPackedUp().parallelStream()
                .mapToDouble(p -> getResultLines().parallelStream()
                        .mapToDouble(l -> l.calculateDistanceToPoint(p))
                        .min().getAsDouble())
                .average().getAsDouble());
        System.out.println("正方形:" + getMatchingRate());
    }

    @Override
    public Square getAnalyzedGraph() {
        return (Square) super.getAnalyzedGraph();
    }

    /**
     * 获取模拟图形的线
     *
     * @return 线
     */
    private List<SimulatedLine> getResultLines() {
        double s = getAnalyzedGraph().getSideLength();
        double rotate = getAnalyzedGraph().getRotate();
        int x = getAnalyzedGraph().getCenter().getX();
        int y = getAnalyzedGraph().getCenter().getY();

        double r = s * Math.pow(0.5, 0.5);
        int h1 = (int) (r * Math.cos(Math.toRadians(45 - rotate)) * Math.pow(2, 0.5) / 2);
        int h2 = (int) (r * Math.sin(Math.toRadians(45 - rotate)) * Math.pow(2, 0.5) / 2);

        Point p1 = new Point(x - h2, y - h1);
        Point p2 = new Point(x + h1, y - h2);
        Point p3 = new Point(x + h2, y + h1);
        Point p4 = new Point(x - h1, y + h2);

        SimulatedLine l1 = new SimulatedLine(p1, p2);
        SimulatedLine l2 = new SimulatedLine(p2, p3);
        SimulatedLine l3 = new SimulatedLine(p3, p4);
        SimulatedLine l4 = new SimulatedLine(p4, p1);

        return new ArrayList<>(Arrays.asList(l1, l2, l3, l4));
    }
}
