package io.hamed.ht_epubreader.bottomsheet;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import io.hamed.ht_epubreader.R;
import io.hamed.htepubreadr.entity.FontEntity;

/**
 * Author: Hamed Taherpour
 * *
 * Created: 10/7/2020
 * *
 * Address: https://github.com/HamedTaherpour
 */
public class ToolsBottomSheet extends BottomSheetDialogFragment {

    private TextView tvFontSize;
    private TextView tvFontFamily;
    private TabLayout tabLayout;
    private OnChangeFontSize onChangeFontSize;
    private OnChangeFontFamily onChangeFontFamily;

    private int fontFamilyPosition = 0;
    private int fontSize = 0;
    private List<FontEntity> listFont = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL, R.style.SheetDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_tools, container, false);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        ((View) getView().getParent()).setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        dismissAllowingStateLoss();
        super.onSaveInstanceState(outState);
    }

    public void setOnChangeFontSize(OnChangeFontSize onChangeFontSize) {
        this.onChangeFontSize = onChangeFontSize;
    }

    public void setOnChangeFontFamily(OnChangeFontFamily onChangeFontFamily) {
        this.onChangeFontFamily = onChangeFontFamily;
    }

    public void setFontFamilyPosition(int fontFamilyPosition) {
        this.fontFamilyPosition = fontFamilyPosition;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setAllFontFamily(List<FontEntity> listFont) {
        this.listFont = listFont;
    }

    private void initView(View view) {
        tabLayout = view.findViewById(R.id.tab_font);
        tvFontFamily = view.findViewById(R.id.tv_font_family);
        tvFontSize = view.findViewById(R.id.tv_font_size);
        SeekBar sbFontSize = view.findViewById(R.id.seek_bar);

        sbFontSize.setProgress(fontSize);
        setUiTextFontSize(fontSize);
        pushUiTabItem(listFont);
        setUiTextFontFamily(listFont.get(fontFamilyPosition).getName());
        tabLayout.getTabAt(fontFamilyPosition).select();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onChangeFontFamily(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        sbFontSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                onChangeFontSize(seekBar.getProgress());
            }
        });
    }

    private void initData() {

    }

    private void onChangeFontSize(int size) {
        if (onChangeFontSize != null) {
            onChangeFontSize.onChangeSize(size);
        }
        setUiTextFontSize(size);
    }

    private void onChangeFontFamily(int position) {
        if (onChangeFontFamily != null) {
            onChangeFontFamily.onChange(position);
        }
        setUiTextFontFamily(listFont.get(position).getName());
    }

    private void pushUiTabItem(List<FontEntity> tabs) {
        for (FontEntity item : tabs) {
            tabLayout.addTab(tabLayout.newTab().setText(item.getName()));
        }
    }

    private void setUiTextFontSize(int size) {
        tvFontSize.setText("FontSize: " + size);
    }

    private void setUiTextFontFamily(String font) {
        tvFontFamily.setText("FontFamily: " + font);
    }
}
