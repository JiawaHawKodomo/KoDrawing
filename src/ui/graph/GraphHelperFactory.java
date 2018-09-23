package ui.graph;

import bl.model.graph.*;
import ui.MainController;


/**
 * @author Shuaiyu Yao
 * @create 2018-09-23 12:54
 */
public class GraphHelperFactory {

    public static GraphHelper newGraphHelper(MainController mainController, Graph graph) {
        if (graph instanceof Circle) {
            return new CircleGraphHelper(mainController, (Circle) graph);
        } else if (graph instanceof Rectangle) {
            return new RectangleGraphHelper(mainController, (Rectangle) graph);
        } else if (graph instanceof SimpleGraph) {
            return new SimpleChangeableGraphHelper(mainController, (SimpleGraph) graph);
        } else if (graph instanceof Square) {
            return new SquareGraphHelper(mainController, (Square) graph);
        } else if (graph instanceof Triangle) {
            return new TriangleGraphHelper(mainController, (Triangle) graph);
        } else {
            throw new RuntimeException("这个对象不能转换为图形");
        }
    }

}
