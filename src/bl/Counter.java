package bl;

/**
 * 计数器, 用于给出标号
 *
 * @author Shuaiyu Yao
 * @create 2018-09-15 15:45
 */
public class Counter {

    private int count;

    public Counter() {
        count = 1;
    }

    public Counter(int first) {
        count = first + 1;
    }

    public int getNextCount() {
        return ++count;
    }

    public int getCurrentCount() {
        return count;
    }
}
