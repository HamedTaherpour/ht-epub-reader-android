package io.hamed.htepubreadr.entity;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Author: Hamed Taherpour
 * *
 * Created: 10/7/2020
 * *
 * Address: https://github.com/HamedTaherpour
 */public class BookEntity {

    private String author;
    private String name;
    private String coverImage;
    private String navPath;
    private List<String> pagePathList;
    private List<SubBookEntity> subBookList;

    public BookEntity(String author, String name, String coverImage, String navPath, List<String> pagePathList, List<SubBookEntity> subBookList) {
        this.author = author;
        this.name = name;
        this.coverImage = coverImage;
        this.navPath = navPath;
        this.pagePathList = pagePathList;
        this.subBookList = subBookList;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public String getNavPath() {
        return navPath;
    }

    public List<SubBookEntity> getSubBookHref() {
        return subBookList;
    }

    public List<String> getPagePathList() {
        return pagePathList;
    }

    @NonNull
    @Override
    public String toString() {
        return "Author: " + author + " Name: " + name + " CoverImage: " + coverImage + " Nav: " + navPath + " Pages: " + pagePathList.size();
    }
}
