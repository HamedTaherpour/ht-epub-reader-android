package io.hamed.htepubreadr.module;

import android.content.Context;


import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.hamed.htepubreadr.app.util.XmlUtil;
import io.hamed.htepubreadr.entity.SubBookEntity;

/**
 * Author: Hamed Taherpour
 * *
 * Created: 10/7/2020
 * *
 * Address: https://github.com/HamedTaherpour
 */
public class EpubReaderExtractModule {

    private static final String KEY_XML_AUTHOR = "dc:creator";
    private static final String KEY_XML_TITLE = "dc:title";
    private static final String KEY_XML_ROOT_FILE = "rootfile";
    private static final String KEY_XML_MANIFEST = "manifest";
    private static final String KEY_XML_META_DATA = "metadata";

    private EpubReaderFileModule pathModule;
    private Element rooElement;
    private String navName = "";

    public EpubReaderExtractModule(EpubReaderFileModule pathModule) {
        this.pathModule = pathModule;
    }

    public void setUp(Context context) throws IOException {
        pathModule.setUp(context);
        pathModule.setOtpFile(getOpfFilePath());
        rooElement = XmlUtil.getRootElement(pathModule.getOtpFilePath());
    }

    private String getOpfFilePath() {
        Element containerElement = XmlUtil.getRootElement(pathModule.getContainerFilePath());
        Node rootFileNode = containerElement.getElementsByTagName(KEY_XML_ROOT_FILE).item(0);
        return XmlUtil.getValueOfAttributes("full-path", rootFileNode);
    }

    public String fetchSubBookHref() {
        NodeList guide = rooElement.getElementsByTagName("guide");
        if (guide.getLength() > 0) {
            Node rootNode = guide.item(0);
            NodeList nodeList = rootNode.getChildNodes();
            Node node = XmlUtil.getNodeOfAttributes(nodeList, "type", "toc");
            if (node != null) {
                return navName = pathModule.getBaseHref(getHref(node));
            }
        }
        return "";
    }

    public String fetchCoverImageHref() {
        Node rootNode = rooElement.getElementsByTagName(KEY_XML_MANIFEST).item(0);
        NodeList nodeList = rootNode.getChildNodes();
        Node node = XmlUtil.getNodeOfAttributes(nodeList, "id", "cover-image");
        if (node != null)
            return pathModule.getBaseHref(getHref(node));
        return null;
    }

    public List<String> fetchAllPagePath() {
        Node manifestNode = rooElement.getElementsByTagName(KEY_XML_MANIFEST).item(0);
        NodeList nodeList = manifestNode.getChildNodes();

        List<String> pages = new ArrayList<>(nodeList.getLength());

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() != Node.ELEMENT_NODE)
                continue;

            if (isPage(node)) {
                String href = pathModule.getBaseHref(getHref(node));
                if (!href.equalsIgnoreCase(navName))
                    pages.add(href);
            }
        }
        return pages;
    }

    public List<SubBookEntity> fetchSubBookHref(String _href) {
        Element element = XmlUtil.getRootElement(_href);

        if (element == null) {
            return new ArrayList<>();
        }

        NodeList navNode = element.getElementsByTagName("a");

        List<SubBookEntity> list = new ArrayList<>();
        for (int i = 0; i < navNode.getLength(); i++) {
            Node nodeItem = navNode.item(i);
            if (nodeItem.getNodeType() != Node.ELEMENT_NODE || !XmlUtil.hasAttribute(nodeItem, "href"))
                continue;

            String href = pathModule.getBaseHref(getHref(nodeItem));
            String title = nodeItem.getChildNodes().item(0).getTextContent();
            list.add(new SubBookEntity(title, href));
        }

        return list;
    }

    public String fetchAuthor() {
        Node metadataNode = rooElement.getElementsByTagName(KEY_XML_META_DATA).item(0);
        Element element = (Element) metadataNode;
        return XmlUtil.getValue(KEY_XML_AUTHOR, element);
    }

    public String fetchTitle() {
        Node metadataNode = rooElement.getElementsByTagName(KEY_XML_META_DATA).item(0);
        Element element = (Element) metadataNode;
        return XmlUtil.getValue(KEY_XML_TITLE, element);
    }

    public String getHref(Node node) {
        return XmlUtil.getValueOfAttributes("href", node);
    }

    private boolean isPage(Node node) {
        return XmlUtil.hasAttribute(node, "media-type", "application/xhtml+xml");
    }

}
