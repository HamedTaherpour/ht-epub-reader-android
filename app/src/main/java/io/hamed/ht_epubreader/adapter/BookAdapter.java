package io.hamed.ht_epubreader.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.hamed.ht_epubreader.R;
import io.hamed.htepubreadr.entity.FontEntity;
import io.hamed.htepubreadr.ui.view.EpubView;
import io.hamed.htepubreadr.ui.view.OnHrefClickListener;
import io.hamed.htepubreadr.util.EpubUtil;

/**
 * Author: Hamed Taherpour
 * *
 * Created: 10/7/2020
 * *
 * Address: https://github.com/HamedTaherpour
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

    private List<String> data;
    private OnHrefClickListener onHrefClickListener;
    private String baseUrl;
    private FontEntity fontEntity;
    private int fontSize = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public EpubView epubView;

        public MyViewHolder(View v) {
            super(v);
            epubView = v.findViewById(R.id.epub_view);
        }

        public void bind(String content) {
            if (fontSize != -1)
                epubView.setFontSize(fontSize);
            if (fontEntity != null)
                epubView.setFont(fontEntity);
            epubView.setBaseUrl(baseUrl);
            if (onHrefClickListener != null)
                epubView.setOnHrefClickListener(onHrefClickListener);
            epubView.setUp(content);
        }
    }

    public BookAdapter(List<String> data, String baseUrl) {
        this.data = data;
        this.baseUrl = baseUrl;
    }

    @Override
    public BookAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String content = "Error";
        try {
            content = EpubUtil.getHtmlContent(data.get(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.bind(content);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public OnHrefClickListener getOnHrefClickListener() {
        return onHrefClickListener;
    }

    public void setOnHrefClickListener(OnHrefClickListener onHrefClickListener) {
        this.onHrefClickListener = onHrefClickListener;
    }

    public FontEntity getFontEntity() {
        return fontEntity;
    }

    public void setFontEntity(FontEntity fontEntity) {
        this.fontEntity = fontEntity;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
