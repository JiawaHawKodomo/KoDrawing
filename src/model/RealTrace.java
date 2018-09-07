package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 实际画过的痕迹
 *
 * @author Shuaiyu Yao
 * @create 2018-09-07 15:58
 */
public class RealTrace implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;//对应图案编号
    private Set<Point> points;

    public RealTrace() {
        id = -1;
        points = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Point> getPoints() {
        return points;
    }

    public void setPoints(Set<Point> points) {
        this.points = points;
    }
}
