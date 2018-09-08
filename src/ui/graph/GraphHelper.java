package ui.graph;


import javafx.scene.layout.Pane;

/**
 * @author Shuaiyu Yao
 * @create 2018-09-08 13:25
 */
public abstract class GraphHelper {

    //是否可选中
    private MouseMode mouseMode;
    //是否已选中
    private boolean selected;

    /**
     * 现在在pane上
     *
     * @param pane 所显示的地方
     */
    public abstract void showOn(Pane pane);

    /**
     * 设置鼠标模式
     */
    public void setMouseMode(MouseMode mouseMode) {
        if (mouseMode != MouseMode.SELECT) {
            unselect();
        }
        this.mouseMode = mouseMode;
    }

    /**
     * 清空选择
     */
    abstract void unselect();

    /**
     * 获取模式
     *
     * @return boolean
     */
    public MouseMode getMouseMode() {
        return mouseMode;
    }

    /**
     * 设置已选中状态
     *
     * @param isSelected
     */
    public abstract void setSelected(boolean isSelected);

    /**
     * 查看是否选中
     *
     * @return
     */
    public boolean isSelected() {
        return selected;
    }

}
