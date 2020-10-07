package io.hamed.ht_epubreader.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: Hamed Taherpour
 * *
 * Created: 10/7/2020
 */
public class MyRecycler extends RecyclerView {

    public MyRecycler(@NonNull Context context) {
        super(context);
    }

    public MyRecycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycler(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean verticalScrollingEnabled = false;

    public void enableVerticalScroll(boolean enabled) {
        verticalScrollingEnabled = enabled;
    }

    public boolean isVerticalScrollingEnabled() {
        return verticalScrollingEnabled;
    }

    @Override
    public int computeVerticalScrollRange() {
        if (isVerticalScrollingEnabled())
            return super.computeVerticalScrollRange();
        return 0;
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent e) {
//        if (isVerticalScrollingEnabled())
//            return super.onInterceptTouchEvent(e);
//        return false;
//    }
}
