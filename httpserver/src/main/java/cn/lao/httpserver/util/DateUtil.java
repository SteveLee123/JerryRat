package cn.lao.httpserver.util;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * @author lao
 * @version 1.0
 * @since 1.0
 */
public class DateUtil {

    private static SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss SSS");

    private DateUtil(){

    }

    /**
     * 获取系统当前时间
     * @return String [yyyy-MM-dd HH:mm:ss SSS]
     */
    public static String getCurrentTime(){
        return dateFormat.format(new Date());
    }

}
