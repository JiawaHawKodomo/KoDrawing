package bl.io.io_model;

import bl.model.Point;
import bl.model.graph.Line;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 实际画过的痕迹
 *
 * @author Shuaiyu Yao
 * @create 2018-09-07 15:58
 */
public class RealTrace implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<List<Point>> points;

    public RealTrace() {
        points = new ArrayList<>();
    }

    public List<List<Point>> getPoints() {
        return points;
    }

    public void setPoints(List<List<Point>> points) {
        this.points = points;
    }
}
