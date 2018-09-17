package bl.analyzer;

import bl.model.Point;
import bl.model.graph.Circle;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2018-09-17 15:05
 */
public class CircleAnalyzer extends Analyzer {

    public CircleAnalyzer(List<Point> trace) {
        super(trace);
    }

    @Override
    public void analyze() {

    }

    @Override
    public Circle getAnalyzedGraph() {
        return (Circle) super.getAnalyzedGraph();
    }
}
