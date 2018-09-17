package bl.analyzer;

import bl.model.Point;
import bl.model.graph.Rectangle;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2018-09-17 15:06
 */
public class RectangleAnalyzer extends Analyzer {


    public RectangleAnalyzer(List<Point> trace) {
        super(trace);
    }

    @Override
    public void analyze() {

    }

    @Override
    public Rectangle getAnalyzedGraph() {
        return (Rectangle) super.getAnalyzedGraph();
    }
}
