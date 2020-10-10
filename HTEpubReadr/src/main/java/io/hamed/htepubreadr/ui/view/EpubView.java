package io.hamed.htepubreadr.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import io.hamed.htepubreadr.module.HtmlBuilderModule;
import io.hamed.htepubreadr.entity.FontEntity;
import io.hamed.htepubreadr.entity.HtmlBuilderEntity;
import io.hamed.htepubreadr.util.EpubUtil;

/**
 * Author: Hamed Taherpour
 * *
 * Created: 10/7/2020
 * *
 * Address: https://github.com/HamedTaherpour
 */
public class EpubView extends WebView {

    private OnHrefClickListener onHrefClickListener;
    private String baseUrl;
    private int fontSize;
    private FontEntity fontEntity;
    private String content;

    public EpubView(Context context) {
        super(context);
    }

    public EpubView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EpubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public OnHrefClickListener getOnHrefClickListener() {
        return onHrefClickListener;
    }

    public void setOnHrefClickListener(OnHrefClickListener onHrefClickListener) {
        this.onHrefClickListener = onHrefClickListener;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFontDefaultSize(int size) {
        this.fontSize = size;
    }

    public void setFontSize(int size) {
        this.fontSize = size;
        getSettings().setDefaultFontSize(size);
//            loadUrl("javascript:setFontSize('" + size + "px')");
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFont(FontEntity fontEntity) {
        this.fontEntity = fontEntity;
    }

    public FontEntity getFont() {
        return fontEntity;
    }

    public void setUp() {
        getSettings().setJavaScriptEnabled(true);
        getSettings().setDefaultTextEncodingName("utf-8");
        getSettings().setDomStorageEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setWebContentsDebuggingEnabled(true);
        }

        setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.i("@MY_APP", "onConsoleMessage: " + consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }
        });

        setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if ((url.endsWith(".xhtml") || url.endsWith(".html")) && getOnHrefClickListener() != null) {
                    getOnHrefClickListener().onClick(url);
                    return true;
                } else
                    return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });

        String html;
        if (getFont() != null)
            html = generateHtmlContent(getContent(), getFont().getUrl());
        else
            html = generateHtmlContent(getContent());

        loadDataWithBaseURL(getBaseUrl(), html, "text/html", "UTF-8", null);
    }

    private String generateHtmlContent(String htmlContent, String fontFamily) {
        try {
            htmlContent = htmlContent.replaceAll("src=\"../", "src=\"" + getBaseUrl() + "");
            htmlContent = htmlContent.replaceAll("href=\"../", "href=\"" + getBaseUrl() + "");
        } catch (Exception e) {
            e.printStackTrace();
            htmlContent = "404";
        }
        HtmlBuilderModule htmlBuilderModule = new HtmlBuilderModule();
        HtmlBuilderEntity entity = new HtmlBuilderEntity(
                "img{display: inline; height: auto; max-width: 100%;}",
                fontFamily,
                htmlContent
        );
        return htmlBuilderModule.getBaseContent(entity);
    }

    private String generateHtmlContent(String htmlPage) {
        return generateHtmlContent(htmlPage, "");
    }
}
