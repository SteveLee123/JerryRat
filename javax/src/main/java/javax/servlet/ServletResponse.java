package javax.servlet;

import java.io.PrintWriter;

/**
 * 封装响应参数的接口规范
 * @author lao
 * @version 1.0
 * @since 1.0
 */
public interface ServletResponse {

    void setWriter(PrintWriter out);
    PrintWriter getWriter();






}
