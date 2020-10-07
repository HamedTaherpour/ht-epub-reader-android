package io.hamed.htepubreadr.module;

import io.hamed.htepubreadr.entity.HtmlBuilderEntity;

/**
 * Author: Hamed Taherpour
 * *
 * Created: 10/5/2020
 */
public class HtmlBuilderModule {

    private String getHeadContent(String fontFamily, String css) {
        String html =
                "<head>" +
                        "<meta http-equiv=\"content-type\" content=\"text/html;\" charset=\"UTF-8\">";
        if (fontFamily != null && !fontFamily.isEmpty())
            html += "<link rel=\"stylesheet\" href=\"" + fontFamily + "\" />";
        if (css != null && !css.isEmpty())
            html += "<style>" + css + "</style>" +
                    "</head>";
        return html;
    }

    private String getJs() {
        return "<script type=\"text/javascript\">" +
                "function setFontSize(size)" +
                "{" +
                "console.log(size);"+
                "document.querySelector('html').style['font-size'] = size;" +
                "}" +
                "</script>";
    }

    public String getBaseContent(HtmlBuilderEntity entity) {
        return "<html>" +
                getHeadContent(entity.getFontFamily(), entity.getCss()) +
                "<body>" +
                entity.getBody() +
                "</body>" +
                getJs() +
                "</html>";
    }
}
