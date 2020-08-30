package cn.lao.httpserver.core;

import cn.lao.httpserver.util.Logger;
import org.dom4j.DocumentException;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * httpserver程序主入口
 *
 * @author lao
 * @version 1.0
 * @since 1.0
 */
public class BootStrap {

    /**
     * 主程序
     *
     * @param args
     */
    public static void main(String[] args) {
        start();
    }

    /**
     * 主程序入口
     */
    public static void start() {

        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        BufferedReader br = null;

        try {
            Logger.log("httpserver start");
            long start = System.currentTimeMillis();

            //获取系统端口号
            int port = ServerParser.getPort();
            Logger.log("httpserver-port: " + port);
            serverSocket = new ServerSocket(port);

            //解析web.xml
            String[] webAppNames = {"oa"};//以后可以考虑优化，比如把项目放进webapps下等
            WebParser.parser(webAppNames);

            //记录启动结束时间
            long end = System.currentTimeMillis();
            Logger.log("httpserver started: " + (end - start) + "ms");

            while (true) {

                //开始监听，此时程序处于等待状态，等待客户端的消息
                clientSocket = serverSocket.accept();

                /*//接收客户端消息
                br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //接收消息
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    System.out.println(temp);
                }*/

                //这里可以考虑用线程池来处理
                new Thread(new HandlerRequest(clientSocket)).start();



            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            /*if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/

            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }


}
