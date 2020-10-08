package io.hamed.htepubreadr.module;

import android.content.Context;


import java.io.File;
import java.io.IOException;

import io.hamed.htepubreadr.app.util.FileUtil;
import io.hamed.htepubreadr.app.util.StringUtils;
import io.hamed.htepubreadr.app.util.ZipUtil;

/**
 * Author: Hamed Taherpour
 * *
 * Created: 10/7/2020
 * *
 * Address: https://github.com/HamedTaherpour
 */
public class EpubReaderFileModule {

    private static final String META_INF_DIRECTORY = "META-INF/";
    private static final String CONTENT_DIRECTORY = "OEBPS/";
    private static final String CONTAINER_XML_FILE = "container.xml";
    private static final String CACHE_DIRECTORY = "book/";
    private String epubFilePath;
    private String unzipFilePath;

    public EpubReaderFileModule(String epubFilePath) {
        this.epubFilePath = epubFilePath;
    }

    public void setUp(Context context) throws IOException {
        File targetDirectory = FileUtil.createFileCache(context, CACHE_DIRECTORY + StringUtils.getBaseName(epubFilePath));
        unzipFilePath = targetDirectory.getPath();
        ZipUtil.unzip(new File(epubFilePath), targetDirectory);
    }

    public String getAbsolutePath() {
        return "file://" + getContentFolderPath();
    }

    public String getBaseHref(String href) {
        return getContentFolderPath() + href;
    }

    public String getOtpFilePath(String otpFilePath){
        return getUnzipFolderPath() + otpFilePath;
    }

    public String getContainerFilePath() {
        return getMetaInfoFolderPath() + CONTAINER_XML_FILE;
    }

    private String getContentFolderPath() {
        return getUnzipFolderPath() + CONTENT_DIRECTORY;
    }

    private String getMetaInfoFolderPath() {
        return getUnzipFolderPath() + META_INF_DIRECTORY;
    }

    private String getUnzipFolderPath() {
        return unzipFilePath + "/";
    }

}
