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
}
