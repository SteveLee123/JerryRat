package cn.lao.httpserver.util;

/**
 * 日志记录器
 * @author lao
 * @version 1.0
 * @since 1.0
 */
public class Logger {

    private Logger(){

    }


    /**
     * 普通日志记录器
     * @param msg
     */
    public static void log(String msg){
        /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        Date nowTime = new Date();
        String nowTimeStr = dateFormat.format(nowTime);*/

        System.out.println(" [INFO] "+ DateUtil.getCurrentTime()+" "+ msg);


    }


}
