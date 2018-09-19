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

    private List<List<Point>> trace;
    private double matchingRate;//匹配度
    private Graph graph;

    public Analyzer(List<List<Point>> trace) {
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

    protected List<List<Point>> getTrace() {
        return trace;
    }

    protected void setTrace(List<List<Point>> trace) {
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
    List<Point> pickPointsBy(int length) {
        List<Point> packedUp = getPackedUp();
        int originalLength = packedUp.size();
        List<Point> newList = new ArrayList<>();
        for (int i = 0; i < originalLength; i += length) {
            newList.add(packedUp.get(i));
        }
        return newList;
    }

    /**
     * 将trace转为单层list
     *
     * @return
     */
    List<Point> getPackedUp() {
        List<Point> packedUp = new ArrayList<>();
        for (List<Point> l : trace) {
            packedUp.addAll(l);
        }
        return packedUp;
    }

    /**
     * 计算两点的斜率, 从p1到p2
     *
     * @param p1 起点
     * @param p2 终点
     * @return 如果p1, p2竖直,返回最大double, 否则返回斜率
     */
    double getSlopeOfTwoPoints(Point p1, Point p2) {
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
    double calculateAngle(Point p1, Point middle, Point p2) {
        double a = p1.calculateDistanceToPoint(p2);
        double b = p1.calculateDistanceToPoint(middle);
        double c = p2.calculateDistanceToPoint(middle);
        return Math.acos((b * b + c * c - a * a) / (2 * b * c));
    }

    /**
     * 判断Trace的子列表是否是直线
     *
     * @return true如果是
     */
    boolean isAllTraceLine() {
        return getTrace().parallelStream()
                .map(this::isLine).reduce((a, b) -> a && b).orElse(true);
    }

    /**
     * 判断一个列表能否拟合成直线
     *
     * @param list list
     * @return true 如果是
     */
    boolean isLine(List<Point> list) {
        return getMatchRateOfLine(list) <= 3.0;
    }

    /**
     * 获取拟合的度数
     *
     * @param list list
     * @return double
     */
    double getMatchRateOfLine(List<Point> list) {
        return list.parallelStream()
                .mapToDouble(p -> new SimulatedLine(list.get(0), list.get(list.size() - 1)).calculateDistanceToPoint(p))
                .average().getAsDouble();
    }


    /**
     * 模拟线的类
     */
    class SimulatedLine {
        final Point p1;
        final Point p2;
        final double length;
        final double slope;

        SimulatedLine(Point p1, Point p2) {
            //设置点, 永远为左上为p1, 右下为p2, 左右优先级大于上下
            if (p1.getX() < p2.getX()) {
                this.p1 = p1;
                this.p2 = p2;
            } else if (p1.getX() > p2.getX()) {
                this.p1 = p2;
                this.p2 = p1;
            } else if (p1.getY() < p2.getY()) {
                this.p1 = p1;
                this.p2 = p2;
            } else {
                this.p1 = p2;
                this.p2 = p1;
            }

            length = p1.calculateDistanceToPoint(p2);
            slope = getSlopeOfTwoPoints(p1, p2);
        }

        /**
         * 计算两条直线交点
         *
         * @param line 线
         * @return 交点Point
         */
        Point calculateIntersection(SimulatedLine line) {
            if (slope == line.slope) {
                return null;
            }
            double x = (line.p1.getY() - p1.getY() + slope * p1.getX() - line.slope * line.p1.getX()) / (slope - line.slope);
            double y = slope * (x - p1.getX()) + p1.getY();
            return new Point((int) x, (int) y);
        }

        /**
         * 一个点到这条线段的最短距离
         *
         * @param point 点
         * @return 距离
         */
        double calculateDistanceToPoint(Point point) {
            double s1 = p1.calculateDistanceToPoint(point);
            double s2 = p2.calculateDistanceToPoint(point);
            if (s1 * s1 + length * length <= s2 * s2) {
                return s1;
            } else if (s2 * s2 + length * length <= s1 * s1) {
                return s2;
            } else {
                return Math.abs((slope * point.getX() - point.getY() + p1.getY() - slope * p1.getX()) / (Math.pow(slope * slope + 1, 0.5)));
            }
        }

        /**
         * 取中点
         *
         * @return 中点
         */
        Point getMidpoint() {
            return new Point((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2);
        }

        /**
         * 中垂线, 长度和改线段长度一样
         *
         * @return 线
         */
        SimulatedLine getMidperpendicular() {
            Point midpoint = getMidpoint();
            double h1 = length / 2 * Math.sin(Math.atan(Math.abs(slope)));
            double h2 = length / 2 * Math.cos(Math.atan(Math.abs(slope)));

            Point rp1 = new Point((int) (midpoint.getX() + h1), (int) (midpoint.getY() - h2));
            Point rp2 = new Point((int) (midpoint.getX() - h1), (int) (midpoint.getY() + h2));
            return new SimulatedLine(rp1, rp2);
        }
    }
}
