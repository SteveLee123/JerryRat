package cn.yan.oa.servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * 处理登录业务的java程序，该程序由webApp开发人员开发，由web服务器人员负责调用
 * @author webApp开发人员
 * @version 1.0
 * @since 1.0
 */
public class LoginServlet implements Servlet{

    public void service(ServletRequest request, ServletResponse servletResponse){
        System.out.println("正在验证身份，请稍等。。。");
        //获取响应流对象
        PrintWriter out = servletResponse.getWriter();
        out.print("<html>");
        out.print("<head>");
        out.print("<title>正在验证</title>");
        out.print("<meta content='text/html;charset=utf-8' />");
        out.print("</head>");
        out.print("<body>");
        out.print("<center><font size='35px' color='blue'>正在验证身份，请稍等</font></center>");
        out.print("</body>");




        out.print("</html>");





    }






}
