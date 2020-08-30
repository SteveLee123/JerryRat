package cn.lao.httpserver.core;

import javax.servlet.Servlet;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet对象缓存池
 * @author lao
 * @version 1.0
 * @since 1.0
 *
 */
public class ServletCache {

    private static Map<String,Servlet> servletCacheMap = new HashMap<>();
    public static void put(String urlPattern,Servlet servlet){
        servletCacheMap.put(urlPattern,servlet);

    }

    public static  Servlet get(String urlPattern){
        return servletCacheMap.get(urlPattern);
    }


}
