package bl.analyzer;

import bl.model.Point;
import bl.model.graph.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * 分析器
 *
 * @author Shuaiyu Yao
 * @create 2018-09-17 14:58
 */
public abstract class Analyzer {

    private List<Point> trace;
    private double matchingRate;//匹配度
    private Graph graph;

    public Analyzer(List<Point> trace) {
        setTrace(trace);
        setMatchingRate(0);
        analyze();
    }

    public abstract void analyze();

    public Graph getAnalyzedGraph() {
        return graph;
    }

    public double getMatchingRate() {
        return matchingRate;
    }

    protected List<Point> getTrace() {
        return trace;
    }

    protected void setTrace(List<Point> trace) {
        this.trace = trace;
    }

    protected void setMatchingRate(double matchingRate) {
        this.matchingRate = matchingRate;
    }

    protected void setGraph(Graph graph) {
        this.graph = graph;
    }

    /**
     * 将trace简化, 选取点
     *
     * @param length 选取点的区间
     */
    protected void pickPointsBy(int length) {
        int originalLength = trace.size();
        List<Point> newList = new ArrayList<>();
        for (int i = 0; i < originalLength; i += length) {
            newList.add(trace.get(i));
        }
        setTrace(newList);
    }

    /**
     * 计算两点的斜率, 从p1到p2
     *
     * @param p1 起点
     * @param p2 终点
     * @return 如果p1, p2竖直,返回最大double, 否则返回斜率
     */
    protected double getSlopeOfTwoPoints(Point p1, Point p2) {
        if (p1.getX() == p2.getX()) {
            return Double.MAX_VALUE;
        }

        return (double) (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
    }

    /**
     * 计算夹角
     *
     * @param p1     点1
     * @param middle 夹角顶点
     * @param p2     点2
     * @return 夹角弧度值
     */
    protected double calculateAngle(Point p1, Point middle, Point p2) {
        double a = Math.pow(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2), 0.5);
        double b = Math.pow(Math.pow(p1.getX() - middle.getX(), 2) + Math.pow(p1.getY() - middle.getY(), 2), 0.5);
        double c = Math.pow(Math.pow(p2.getX() - middle.getX(), 2) + Math.pow(p2.getY() - middle.getY(), 2), 0.5);
        return Math.acos((b * b + c * c - a * a) / (2 * b * c));
    }

    /**
     * 模拟线的类
     */
    protected class SimulatedLine {
        Point p1;
        Point p2;
        double length;
        double slope;

        SimulatedLine(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
            length = Math.pow(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2), 0.5);
            slope = getSlopeOfTwoPoints(p1, p2);
        }

        /**
         * 计算两条直线交点
         *
         * @param line
         * @return
         */
        Point calculateIntersection(SimulatedLine line) {
            if (slope == line.slope) {
                return null;
            }
            double x = (line.p1.getY() - p1.getY() + slope * p1.getX() - line.slope * line.p1.getX()) / (slope - line.slope);
            double y = slope * (x - p1.getX()) + p1.getY();
            return new Point((int) x, (int) y);
        }
    }
}
