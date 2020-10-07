package io.hamed.htepubreadr.util;


import java.util.ArrayList;
import java.util.List;

import io.hamed.htepubreadr.app.util.FileUtil;

public class EpubUtil {

    public static String getHtmlContent(String href) throws Exception {
        return FileUtil.getStringFromFile(href);
    }

    public static List<String> getHtmlContentList(List<String> hrefList) throws Exception {
        List<String> list = new ArrayList<>(hrefList.size() - 1);
        for (String href : hrefList) {
            list.add(getHtmlContent(href));
        }
        return list;
    }
}
