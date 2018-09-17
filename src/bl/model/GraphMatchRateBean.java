package bl.model;

import bl.model.graph.Graph;

/**
 * 包含一个Graph和一个匹配度
 *
 * @author Shuaiyu Yao
 * @create 2018-09-17 15:28
 */
public class GraphMatchRateBean {

    private Graph graph;//图形
    private double matchingRate;//匹配度

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public double getMatchingRate() {
        return matchingRate;
    }

    public void setMatchingRate(double matchingRate) {
        this.matchingRate = matchingRate;
    }
}
