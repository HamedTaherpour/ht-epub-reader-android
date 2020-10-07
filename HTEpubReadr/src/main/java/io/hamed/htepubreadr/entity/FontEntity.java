package io.hamed.htepubreadr.entity;

/**
 * Author: Hamed Taherpour
 * *
 * Created: 10/5/2020
 */
public class FontEntity {

    private String url;
    private String name;

    public FontEntity(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
