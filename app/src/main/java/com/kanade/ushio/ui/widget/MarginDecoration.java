package com.kanade.ushio.ui.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * from https://github.com/chiuki/android-recyclerview
 */
public class MarginDecoration extends RecyclerView.ItemDecoration {
    private int margin;

    public MarginDecoration(int margin) {
        this.margin = margin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = margin;
        outRect.top = margin;
        outRect.right = margin;
        outRect.bottom = margin;
    }
}
