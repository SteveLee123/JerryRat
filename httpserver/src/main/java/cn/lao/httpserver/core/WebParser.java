package cn.lao.httpserver.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 解析服务器中的wen.xml配置文件
 * @author lao
 * @version 1.0
 * @since 1.0
 */
public class WebParser {

    public static Map<String,Map<String,String>> servletMaps = new HashMap<String, Map<String, String>>();

    /**
     * 解析多个webApp的web.xml
     * @param webAppNames
     */
    public static void parser(String[] webAppNames) throws DocumentException {
        for (String webAppName:webAppNames){
            Map<String,String> servletMap = parser(webAppName);
            servletMaps.put(webAppName,servletMap);

        }
    }


    /**
     * 解析单个应用的web.xml配置文件
     * @param webAppName  应用名称
     * @return servletMap
     */
    private static Map<String,String> parser(String webAppName) throws DocumentException {

        //获取web.xml的路径
        String webPath = "webapps/"+webAppName + "/WEB-INF/web.xml";

        //创建解析器
        SAXReader saxReader = new SAXReader();

        //通过解析器的read方法将配置文件读到内存中，生成一个Document对象树
        Document document = saxReader.read(new File(webPath));

        /*获取servlet节点元素*/
        List<Element> selectNodes = document.selectNodes("/web-app/servlet");

        //创建一个servletInfoMap集合，将servlet-name和servlet-class的值当作key和value存放到集合中
        Map<String,String> servletInfoMap = new HashMap<String, String>();

        //遍历selectNodes
        for(Element servletNode:selectNodes){

            //获取servlet-name节点元素对象
            Element servletNameElt = (Element)servletNode.selectSingleNode("servlet-name");
            //获取servletNameElt节点元素对象的值
            String servletName = servletNameElt.getStringValue();

            //获取servlet-class节点元素对象
            Element servletClassElt = (Element)servletNode.selectSingleNode("servlet-class");
            //获取servletClassElt节点元素对象的值
            String servletClassName = servletClassElt.getStringValue();

            //将servletName和servletClassName分别当作key和value存放到servletInfoMap集合中
            servletInfoMap.put(servletName,servletClassName);


        }

        /*获取servlet-mapping节点元素：web-app -> servlet-mapping*/
        List<Element> servletMappingNodes = document.selectNodes("/web-app/servlet-mapping");
        //创建servletMappingInfoMap集合，将servlet-name和url-pattern节点元素对象的值分别当作key和value存放到集合
        HashMap<String, String> servletMappingInfoMap = new HashMap<>();
        //遍历servletMappingNodes
        for (Element servletMappingNode:servletMappingNodes){

            //获取servlet-name节点元素
            Element servletNameElt = (Element)servletMappingNode.selectSingleNode("servlet-name");
            //获取servlet-name节点元素的值
            String servletName = servletNameElt.getStringValue();
            //获取url-pattern节点元素
            Element urlPatternElt = (Element)servletMappingNode.selectSingleNode("url-pattern");
            //获取url-pattern节点元素的值
            String urlPattern = urlPatternElt.getStringValue();

            //放到集合里面去
            servletMappingInfoMap.put(servletName,urlPattern);
        }


        /*将两个Map继承为一个servletMap，url-pattern -> servletClassName*/

        //获取servletInfoMap或者servletMappingInfoMap的keySet
        Set<String> servletNames = servletInfoMap.keySet();
        //创建一个servletMap集合，将servletMappingInfoMap的value和servletInfoMap的value分别当作key和value存放
        Map<String,String> servletMap = new HashMap<String, String>();
        //开始遍历servletNames
        for (String servletName:servletNames){
            //获取servletMappingInfoMapping集合中的value：url-pattern
            String urlPattern = servletMappingInfoMap.get(servletName);
            //获取servletInfo集合中的value：servletClass
            String servletClassName = servletInfoMap.get(servletName);

            //放进集合
            servletMap.put(urlPattern,servletClassName);
        }






        return servletMap;
    }



}
