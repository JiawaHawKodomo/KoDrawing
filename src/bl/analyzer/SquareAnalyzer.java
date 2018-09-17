package bl.analyzer;

import bl.model.Point;
import bl.model.graph.Square;

import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2018-09-17 15:06
 */
public class SquareAnalyzer extends Analyzer {


    public SquareAnalyzer(List<Point> trace) {
        super(trace);
    }

    @Override
    public void analyze() {

    }

    @Override
    public Square getAnalyzedGraph() {
        return (Square) super.getAnalyzedGraph();
    }
}
