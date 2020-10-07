# HTEpubReadr
ePub reader and parser library for android

[![](https://jitpack.io/v/HamedTaherpour/ht-epub-readr.svg)](https://jitpack.io/#HamedTaherpour/ht-epub-readr)


## Demo
<div style="dispaly:flex">
    <img src="/sample1.gif" width="24%">
    <img src="/sample2.gif" width="24%">
</div>

##### [Example project](https://github.com/HamedTaherpour/ht-epub-readr/tree/master/app)

## Build
##### Step 1. Add the JitPack repository to your build file
```build
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
##### Step 2. Add the dependency
```build
dependencies {
    implementation 'com.github.HamedTaherpour:ht-epub-readr:0.0.1'
}
```

## Usage
### Manifest
##### Step 1. Add permission your manifest file
```xml
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
### Layout.xml
##### Step 2. Create layout and don't forget add id (root_container) like this
```layout
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:cardElevation="3dp">

    <io.hamed.htepubreadr.ui.view.EpubView
        android:id="@+id/epub_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:padding="10dp" />

</androidx.cardview.widget.CardView>
```
### Java
##### Step 3. Create EpubReaderComponent and fetch ePub data
```java
try {
    EpubReaderComponent epubReader = new EpubReaderComponent(filePath);
    BookEntity bookEntity = epubReader.make(this);
} catch (Exception ex) {
    ex.printStackTrace();
}
```
### BookEntity
##### Get all page path
```java
List<String> allPage = bookEntity.getPagePathList();
```
##### Get book name
```java
String bookName = bookEntity.getName();
```
##### Get author name
```java
String authorName = bookEntity.getAuthor();
```
##### Get over image
```java
String coverImage = bookEntity.getCoverImage();
```
##### Get Sub Book Href (path from local file)
```java
List<SubBookEntity> allPage = bookEntity.getSubBookHref();
```
##### SetUp EpubView
```java
// set file path
epubView.setBaseUrl(epubReader.getAbsolutePath());
epubView.setPathPage(allPage.get(0));
epubView.setUp();
```
##### Set font
```java
FontEntity fontEntity = new FontEntity( url , name);
epubView.setFont(fontEntity);
```
##### Set font size
```java
epubView.setFontSize(15);
```
##### On hyper links click listener
```java
epubView.setOnHrefClickListener(new OnHrefClickListener() {
    @Override
    public void onClick(String href) {

    }
});
```