package model;

import model.graph.Graph;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 整个画面的数据结构
 *
 * @author Shuaiyu Yao
 * @create 2018-09-07 14:26
 */
public class PictureScene implements Serializable {

    private static final long serialVersionUID = 1L;

    private Set<RealTrace> realTrace;//用户实际画过的痕迹
    private Set<Graph> calculatedGraphs;//计算获得的图形

    public PictureScene() {
        this.realTrace = new HashSet<>();
        this.calculatedGraphs = new HashSet<>();
    }

    public Set<RealTrace> getRealTrace() {
        return realTrace;
    }

    public void setRealTrace(Set<RealTrace> realTrace) {
        this.realTrace = realTrace;
    }

    public Set<Graph> getCalculatedGraphs() {
        return calculatedGraphs;
    }

    public void setCalculatedGraphs(Set<Graph> calculatedGraphs) {
        this.calculatedGraphs = calculatedGraphs;
    }
}
