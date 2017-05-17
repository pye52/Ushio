package com.kanade.ushio.ui.subject_main.adapter;

import android.graphics.Paint;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kanade.ushio.R;
import com.kanade.ushio.entity.Ep;

import java.util.List;

import static com.kanade.ushio.entity.Ep.*;
import static com.kanade.ushio.entity.WatchStatus.*;

public class SubjectEpAdapter extends BaseQuickAdapter<Ep, BaseViewHolder> {
    public SubjectEpAdapter(List<Ep> list) {
        super(R.layout.rv_item_subject_ep, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, Ep item) {
        TextView sortTv = helper.getView(R.id.subject_ep_sort);

        String sort = String.valueOf(item.getSort());
        sortTv.setText(sort);
        int colorInt;
        if (item.getStatus().equals(AIR)) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                colorInt = mContext.getColor(android.R.color.white);
            } else {
                colorInt = mContext.getResources().getColor(android.R.color.white);
            }
            sortTv.setTextColor(colorInt);

            // set box style by watch status
            switch (item.getWatchStatus().getId()) {
                case WISH:
                    sortTv.setBackgroundResource(R.color.primary_darker);
                    sortTv.setPaintFlags(sortTv.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    break;
                case WATCHED:
                    sortTv.setBackgroundResource(R.color.primary);
                    sortTv.setPaintFlags(sortTv.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    break;
                case DROP:
                    sortTv.setBackgroundResource(R.color.grey_500);
                    sortTv.setPaintFlags(sortTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    break;
                default:
                    sortTv.setBackgroundResource(R.color.primary_light);
                    sortTv.setPaintFlags(sortTv.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

            }
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                colorInt = mContext.getColor(R.color.text_primary);
            } else {
                colorInt = mContext.getResources().getColor(R.color.text_primary);
            }
            sortTv.setTextColor(colorInt);
            sortTv.setBackgroundResource(R.color.grey_300);
        }
    }
}
