package io.hamed.htepubreadr.app.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Author: Hamed Taherpour
 * *
 * Created: 10/7/2020
 * *
 * Address: https://github.com/HamedTaherpour
 */
public class XmlUtil {

    public static Element getRootElement(String path) {
        try {
            InputStream is = new FileInputStream(path);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element rooElement = doc.getDocumentElement();
            rooElement.normalize();
            return rooElement;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    public static String getValueOfAttributes(String tag, Node node) {
        return node.getAttributes().getNamedItem(tag) != null ? node.getAttributes().getNamedItem(tag).getNodeValue() : "";
    }

    public static Node getNodeOfAttributes(NodeList nodeList, String key, String value) {
        Node node = null;
        for (int x = 0; x < nodeList.getLength(); x++) {
            if (nodeList.item(x).getNodeType() != Node.ELEMENT_NODE)
                continue;
            if (hasAttribute(nodeList.item(x), key, value)) {
                node = nodeList.item(x);
                break;
            }
        }
        return node;
    }

    public static boolean hasAttribute(Node node, String key, String value) {
        return (hasAttribute(node, key) && node.getAttributes().getNamedItem(key).getNodeValue().equalsIgnoreCase(value));
    }

    public static boolean hasAttribute(Node node, String key) {
        return node.getAttributes().getNamedItem(key) != null;
    }

}
