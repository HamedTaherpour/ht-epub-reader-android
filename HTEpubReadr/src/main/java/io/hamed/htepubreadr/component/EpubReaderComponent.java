package io.hamed.htepubreadr.component;


import android.content.Context;

import java.io.IOException;

import io.hamed.htepubreadr.entity.BookEntity;
import io.hamed.htepubreadr.module.EpubReaderExtractModule;
import io.hamed.htepubreadr.module.EpubReaderFileModule;
import io.hamed.htepubreadr.util.EpubUtil;

/**
 * Author: Hamed Taherpour
 * *
 * Created: 10/7/2020
 * *
 * Address: https://github.com/HamedTaherpour
 */
public class EpubReaderComponent {

    public static final int PAGE_NOT_FOUND = 0x404;

    private EpubReaderFileModule epubReaderFileModule;
    private EpubReaderExtractModule epubReaderExtractModule;
    private BookEntity entity;

    public EpubReaderComponent(String path) {
        epubReaderFileModule = new EpubReaderFileModule(path);
        epubReaderExtractModule = new EpubReaderExtractModule(epubReaderFileModule);
    }

    public BookEntity make(Context context) throws IOException {
        epubReaderExtractModule.setUp(context);

        String navHref = epubReaderExtractModule.fetchSubBookHref();
        entity = new BookEntity(
                epubReaderExtractModule.fetchAuthor(),
                epubReaderExtractModule.fetchTitle(),
                epubReaderExtractModule.fetchCoverImageHref(),
                navHref,
                epubReaderExtractModule.fetchAllPagePath(),
                epubReaderExtractModule.fetchSubBookHref(navHref)
        );
        return entity;
    }

    public BookEntity getBook() {
        return entity;
    }

    public String getAbsolutePath() {
        return epubReaderFileModule.getAbsolutePath();
    }

    public int getPagePositionByHref(String href) {
        int i = 0;
        for (String _href : getBook().getPagePathList()) {
            if (href.contains(_href))
                return i;
            i++;
        }
        return PAGE_NOT_FOUND;
    }
}
