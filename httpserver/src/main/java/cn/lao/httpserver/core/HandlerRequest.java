package cn.lao.httpserver.core;

import cn.lao.httpserver.util.Logger;

import javax.servlet.Servlet;
import java.io.*;
import java.net.Socket;
import java.util.Map;

/**
 * 处理客户端请求
 * @author lao
 * @version 1.0
 * @since 1.0
 *
 *
 */
public class HandlerRequest implements Runnable{

    public Socket clientSocket;
    public HandlerRequest(Socket clientSocket){
        this.clientSocket = clientSocket;
    }


    public void run() {

        //处理客户端请求

        BufferedReader br = null;
        PrintWriter out = null;
        Logger.log("httpserver thread "+Thread.currentThread().getName());
        try {
            //接收客户端向消息
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //获取响应流对象
            out = new PrintWriter(clientSocket.getOutputStream());

            //获取请求协议的请求行
            String requestLine = br.readLine();
            if (requestLine!=null) {
                String requestURI = requestLine.split(" ")[1];
                //判断用户请求是否为一个静态页面
                if (requestURI.endsWith(".html") || requestURI.endsWith("htm")){
                    responseStaticPage(requestURI,out);

                }else {//动态资源：java程序业务处理类
                    //requestURI: /oa/login?username=zhangsan&password=111
                    //servletPath: oa/login?username=zhangsan&password=111
                    String servletPath = requestURI.substring(1);

                    //判断servletPath是否包含参数
                    if (servletPath.contains("?")){
                        servletPath = servletPath.split("[?]")[0];
                        //servletPath: oa/login
                    }

/*                    //这里以后可以考虑注入
                    if ("/oa/login".equals(servletPath)){
                        LoginServlet loginServlet = new LoginServlet();
                        loginServlet.service();

                    }*/

                    //获取应用的名称：oa在servletPath里：oa/login
                    String webAppName = servletPath.split("[/]")[0];

                    //获取Map<url-pattern,servletClassName>
                    Map<String, String> servletMap = WebParser.servletMaps.get(webAppName);
                    //从uri获取url-pattern
                    String urlPattern = servletPath.substring(webAppName.length());//urlPattern:/login

                    //获取servletClassName
                    String servletClassName = null;
                    try {
                        servletClassName = servletMap.get(urlPattern);
                    }catch (NullPointerException e){
                        if (!webAppName.equals("favicon.ico")){//这是浏览器的一个保护机制请求，可以忽略
                            e.printStackTrace();
                        }
                    }


                    if (servletClassName!=null){


                        //获取封装请求参数对象
                        RequestObject requestObject = new RequestObject(requestURI);

                        //获取封装响应参数对象
                        ResponseObject responseObject = new ResponseObject();
                        responseObject.setWriter(out);


                        //拼接响应头
                        out.print("HTTP/1.1 200 OK\n");
                        out.print("Content-Type:text/html;charset=utf-8\n\n");

                        /*缓存池*/
                        //1.如果有，直接拿来使用
                        //2.没有，就创建
                        Servlet servlet = ServletCache.get(urlPattern);
                        if (servlet == null){
                            //通过反射创建该类的对象(它对Servlet接口进行了实现，调用时直接调用抽象方法)
                            Class c = Class.forName(servletClassName);
                            servlet = (Servlet) c.newInstance();
                            ServletCache.put(urlPattern,servlet);
                        }


                        servlet.service(requestObject,responseObject);

                        Logger.log(servlet.toString());





                    }else {//找不到动态资源404
                        StringBuilder html = new StringBuilder();
                        html.append("HTTP/1.1 404 NotFound\n");
                        html.append("Content-Type:text/html;charset=utf-8\n\n");
                        html.append("<html>");
                        html.append("<head>");
                        html.append("<title>404-错误</title>");
                        html.append("<meta content='text/html;charset=utf-8'/>");
                        html.append("</head>");
                        html.append("<body>");
                        html.append("<center><font size='35px' color='red'>404 NotFound</font></center>");
                        html.append("</body>");
                        html.append("</html>");
                        out.print(html);




                    }



                }




                //强制刷新
                out.flush();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            if (br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (clientSocket!=null){
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    /**
     * 处理静态页面
     * @param requestURI
     * @param out
     */
    private void responseStaticPage(String requestURI, PrintWriter out) {



        //requestURI:/oa/index.html
        //静态页面的路径：oa/index.html
        String htmlPath = "webapps" + requestURI;

        BufferedReader br = null;
        try {
            //从oa/index.html文件里读取字符流
            br = new BufferedReader(new FileReader(htmlPath));
            StringBuilder html = new StringBuilder();

            //拼接响应信息（响应头等）
            html.append("HTTP/1.1 200 OK\n");
            html.append("Content-Type:text/html;charset-utf-8\n\n");


            String temp = null;
            while ((temp = br.readLine())!=null){
                html.append(temp);
            }

            //输出html
            out.print(html);

        } catch (FileNotFoundException e) {
            //404
            StringBuilder html = new StringBuilder();
            html.append("HTTP/1.1 404 NotFound\n");
            html.append("Content-Type:text/html;charset=utf-8\n\n");
            html.append("<html>");
            html.append("<head>");
            html.append("<title>404-错误</title>");
            html.append("<meta content='text/html;charset=utf-8'/>");
            html.append("</head>");
            html.append("<body>");
            html.append("<center><font size='35px' color='red'>404 NotFound</font></center>");
            html.append("</body>");
            html.append("</html>");
            out.print(html);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
