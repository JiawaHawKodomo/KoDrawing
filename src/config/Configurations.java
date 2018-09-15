package config;

import java.util.Properties;

/**
 * 全局配置
 *
 * @author Shuaiyu Yao
 * @create 2018-09-08 13:54
 */
public class Configurations {

    //线条初始宽度
    private static double lineOriginalWidth = 3;
    //线条鼠标进度宽度
    private static double lineEnterWidth = 7;
    //线条选中宽度
    private static double lineSelectedWidth = 7;
    //线条点击宽度
    private static double lineClickedWidth = 5;

    //三角形颜色
    private static String triangleColor = "07c92a";
    //正方形颜色
    private static String squareColor = "da0000";
    //长方形颜色
    private static String rectangleColor = "008cd8";
    //圆形颜色
    private static String circlrColor = "ffbf00";

    //画笔粗细
    private static double thickness = 2;


    public static void loadProperties() {
        try {

            Properties properties = new Properties();
            properties.load(Configurations.class.getResourceAsStream("config.properties"));

            String tmp;

            //加载line_original_width
            tmp = properties.getProperty("line_original_width");
            lineOriginalWidth = tmp == null ? lineOriginalWidth : Double.parseDouble(tmp);
            //加载line_selected_width
            tmp = properties.getProperty("line_selected_width");
            lineSelectedWidth = tmp == null ? lineSelectedWidth : Double.parseDouble(tmp);
            //加载line_enter_width
            tmp = properties.getProperty("line_enter_width");
            lineEnterWidth = tmp == null ? lineEnterWidth : Double.parseDouble(tmp);
            //加载line_clicked_width
            tmp = properties.getProperty("line_clicked_width");
            lineClickedWidth = tmp == null ? lineClickedWidth : Double.parseDouble(tmp);

            //加载triangle_color
            tmp = properties.getProperty("triangle_color");
            triangleColor = tmp == null ? triangleColor : tmp;
            //加载square_color
            tmp = properties.getProperty("square_color");
            squareColor = tmp == null ? squareColor : tmp;
            //加载rectangle_color
            tmp = properties.getProperty("rectangle_color");
            rectangleColor = tmp == null ? rectangleColor : tmp;
            //加载circle_color
            tmp = properties.getProperty("circle_color");
            circlrColor = tmp == null ? circlrColor : tmp;

            //加载thickness
            tmp = properties.getProperty("thickness");
            thickness = tmp == null ? thickness : Double.parseDouble(tmp);
        } catch (Exception ignored) {
        }
    }

    public static double getLineOriginalWidth() {
        return lineOriginalWidth;
    }

    public static double getLineSelectedWidth() {
        return lineSelectedWidth;
    }

    public static double getLineEnterWidth() {
        return lineEnterWidth;
    }

    public static double getLineClickedWidth() {
        return lineClickedWidth;
    }

    public static String getTriangleColor() {
        return triangleColor;
    }

    public static String getSquareColor() {
        return squareColor;
    }

    public static String getRectangleColor() {
        return rectangleColor;
    }

    public static String getCirclrColor() {
        return circlrColor;
    }

    public static double getThickness() {
        return thickness;
    }
}
