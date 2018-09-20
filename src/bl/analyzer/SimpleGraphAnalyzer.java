package bl.analyzer;

import bl.model.Point;
import bl.model.graph.SimpleGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2018-09-19 19:32
 */
public class SimpleGraphAnalyzer extends Analyzer {


    public SimpleGraphAnalyzer(List<List<Point>> trace) {
        super(trace);
    }

    @Override
    public void analyze() {
        if (getTrace().isEmpty()) {
            setMatchingRate(Double.MAX_VALUE);
            return;
        }

        SimpleGraph result = new SimpleGraph();
        List<SimulatedLine> lines = new ArrayList<>();
        //先将一次笔迹分离
        getTrace().parallelStream().forEach(l -> lines.addAll(cutIntoLines(l)));
        //将两条直线合并成一条
        List<SimulatedLine> toRemove = new ArrayList<>();
        do {
            toRemove.clear();
            List<SimulatedLine> toAdd = new ArrayList<>();
            for (SimulatedLine l1 : lines) {
                for (SimulatedLine l2 : lines) {
                    if (l1 != l2) {//同一条直线无视
                        if (l1.p2.calculateDistanceToPoint(l2.p1) <= 5 && Math.abs((l1.slope - l2.slope) / l1.slope) <= 0.3) {
                            SimulatedLine newLine = new SimulatedLine(l1.p1, l2.p2);
                            toAdd.add(newLine);
                        }
                    }
                }
            }
            lines.removeAll(toRemove);
            lines.addAll(toAdd);
            toAdd.clear();
        } while (!toRemove.isEmpty());

        lines.forEach(l -> result.add(l.p1, l.p2));
        setGraph(result);
        setMatchingRate(Double.MAX_VALUE);
    }

    /**
     * 把一个不是直线的列表切割成全是直线的列表
     *
     * @param points 不是直线
     * @return 全是直线的列表
     */
    private List<SimulatedLine> cutIntoLines(List<Point> points) {
        List<SimulatedLine> result = new ArrayList<>();
        if (isLine(points)) {
            result.add(new SimulatedLine(points.get(0), points.get(points.size() - 1)));
            return result;
        }

        for (int i = points.size() - 1; i > 0; i--) {
            List<Point> sub = points.subList(0, i);
            if (isLine(sub)) {
                result.add(new SimulatedLine(sub.get(0), sub.get(sub.size() - 1)));
                List<Point> rest = points.subList(i, points.size());
                result.addAll(cutIntoLines(rest));
                return result;
            }
        }

        result.add(new SimulatedLine(points.get(0), points.get(points.size() - 1)));
        return result;
    }

    @Override
    public SimpleGraph getAnalyzedGraph() {
        return (SimpleGraph) super.getAnalyzedGraph();
    }
}
