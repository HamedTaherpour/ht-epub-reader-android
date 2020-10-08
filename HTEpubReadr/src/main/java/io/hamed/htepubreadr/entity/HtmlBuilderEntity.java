package io.hamed.htepubreadr.entity;

/**
 * Author: Hamed Taherpour
 * *
 * Created: 10/7/2020
 * *
 * Address: https://github.com/HamedTaherpour
 */
public class HtmlBuilderEntity {

    private String css;
    private String fontFamily;
    private String body;

    public HtmlBuilderEntity(String css, String fontFamily, String body) {
        this.css = css;
        this.fontFamily = fontFamily;
        this.body = body;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
