package cn.lao.httpserver.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 解析server.xml
 * @author lao
 * @version 1.0
 * @since 1.0
 */
public class ServerParser {


    /**
     * 从conf获取服务器的端口号
     *
     * @return
     */
    public static int getPort(){

        int port = 8080;


        try {


            SAXReader saxReader = new SAXReader();
            //解析文件
            Document document = saxReader.read("httpserver/conf/server.xml");
            //根据xpath获取所有connector的标签所对应的Element对象
            Element connectorElt = (Element) document.selectSingleNode("//connector");
            //获取port属性的值
            port = Integer.parseInt(connectorElt.attributeValue("port"));
            return port;
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return 0;
    }


}
