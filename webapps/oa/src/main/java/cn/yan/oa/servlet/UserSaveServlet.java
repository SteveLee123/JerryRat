package cn.yan.oa.servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;

public class UserSaveServlet implements Servlet {

    public void service(ServletRequest request, ServletResponse response) {
        String username = request.getParameterValue("username");
        String gender = request.getParameterValue("gender");
        String[] interest = request.getParameterValues("interest");
        StringBuilder interests = new StringBuilder();
        for (String interestValue:interest){
            interests.append(interestValue).append(" ");

        }

        PrintWriter out = response.getWriter();
        out.print("<html>");
        out.print("<head>");
        out.print("<title>用户信息</title>");
        out.print("<meta content='text/html;charset=utf-8' />");
        out.print("</head>");
        out.print("<body>");
        out.print("用户名： " + username + "<br>");
        out.print("性别： "+ gender + "<br>");
        out.print("兴趣： "+interests + "<br>");
        out.print("</body>");
        out.print("</html>");
    }
}
