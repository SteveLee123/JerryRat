package javax.servlet;



/**
 * 由sun公司制定的Servlet接口规范，服务器根据规范来调用，webApp开发人员来实现
 * @author SUN公司
 * @version 1.0
 * @since 1.0
 */
public interface Servlet {

    /**
     * 处理业务的核心方法
     * @param request
     * @param response
     */
    void service(ServletRequest request, ServletResponse response);



}
