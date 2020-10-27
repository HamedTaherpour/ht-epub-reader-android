# HTePubReader
ePub reader and parser library for android

[![](https://jitpack.io/v/HamedTaherpour/ht-epub-readr.svg)](https://jitpack.io/#HamedTaherpour/ht-epub-readr)

## Demo
<div style="dispaly:flex">
     <img src="https://raw.githubusercontent.com/HamedTaherpour/ht-epub-reader-android/master/sample1.gif" width="24%"> 
</div>

##### [Example project](https://github.com/HamedTaherpour/ht-epub-reader-android/tree/master/app)

## Build
step 1. Add the JitPack repository to your build file
```build
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
step 2. Add the dependency
```build
dependencies {
    implementation 'com.github.HamedTaherpour:ht-epub-reader-android:0.0.3'
}
```

## Usage
### Manifest
step 1. Add permission your manifest file
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
### Layout.xml
step 2. Add `EpubView` to xml code
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
step 3. Create `EpubReaderComponent` and fetch ePub data
```java
try {
    EpubReaderComponent epubReader = new EpubReaderComponent(filePath);
    BookEntity bookEntity = epubReader.make(this);
} catch (Exception ex) {
    ex.printStackTrace();
}
```
### BookEntity
get all page path
```java
List<String> allPage = bookEntity.getPagePathList();
```
get book name
```java
String bookName = bookEntity.getName();
```
get author name
```java
String authorName = bookEntity.getAuthor();
```
get over image
```java
String coverImage = bookEntity.getCoverImage();
```
get Sub Book Href (path from local file)
```java
List<SubBookEntity> allPage = bookEntity.getSubBookHref();
```
setUp EpubView
```java
// set file path
epubView.setBaseUrl(epubReader.getAbsolutePath());
String content = EpubUtil.getHtmlContent(allPage.get(position));
epubView.setHtmlContent(content);
epubView.setUp();
```
set font [see sample css file](https://github.com/HamedTaherpour/sample-assets/blob/master/font/Acme.css)
```java
FontEntity fontEntity = new FontEntity(url,name);
epubView.setFont(fontEntity);
```
set font size
```java
epubView.setFontSize(15);
```
on hyper link click listener
```java
epubView.setOnHrefClickListener(new OnHrefClickListener() {
    @Override
    public void onClick(String href) {

    }
});
```
