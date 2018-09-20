package bl.io.io_model;

import bl.model.graph.Graph;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * 整个画面的数据结构
 *
 * @author Shuaiyu Yao
 * @create 2018-09-07 14:26
 */
public class PictureScene implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<RealTrace, Graph> map = new TreeMap<>();

    public PictureScene() {
    }

    public Map<RealTrace, Graph> getMap() {
        return map;
    }

    public void setMap(Map<RealTrace, Graph> map) {
        this.map = map;
    }
}
