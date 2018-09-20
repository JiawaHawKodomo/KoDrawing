package bl.model.graph;


import bl.model.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shuaiyu Yao
 * @create 2018-09-20 12:11
 */
public class SimpleGraph implements Graph {

    public SimpleGraph() {
        lines = new ArrayList<>();
    }

    private List<Line> lines;

    public void add(Point p1, Point p2) {
        lines.add(new Line(p1, p2));
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }
}
