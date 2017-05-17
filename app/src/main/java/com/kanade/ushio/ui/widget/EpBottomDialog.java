package com.kanade.ushio.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.kanade.ushio.R;
import com.kanade.ushio.entity.Ep;
import com.kanade.ushio.entity.WatchStatus;
import com.kanade.ushio.utils.UtilsKt;

import static android.view.View.GONE;

public class EpBottomDialog extends BottomSheetDialog implements View.OnClickListener {

    private onBottomClickListener listener;
    /**
     * @param ep 传入要展示的集数
     */
    public EpBottomDialog(@NonNull Context context, Ep ep, onBottomClickListener listener) {
        super(context);
        this.listener = listener;
        View view = getLayoutInflater().inflate(R.layout.bottomsheet_progress, null);

        TextView title = (TextView) view.findViewById(R.id.pw_ep_title);
        TextView airInfo = (TextView) view.findViewById(R.id.pw_air_info);
        TextView wishTv = (TextView) view.findViewById(R.id.pw_ep_wish);
        TextView watchedTv = (TextView) view.findViewById(R.id.pw_ep_watched);
        TextView dropTv = (TextView) view.findViewById(R.id.pw_ep_drop);
        TextView undoTv = (TextView) view.findViewById(R.id.pw_ep_undo);

        String airInfoStr = context.getString(R.string.subject_progress_air_info, ep.getDuration(), ep.getAirdate(), ep.getComment());
        title.setText(UtilsKt.strFilter(ep.getNameCn(), ep.getName(), 8));
        airInfo.setText(airInfoStr);
        wishTv.setOnClickListener(this);
        watchedTv.setOnClickListener(this);
        dropTv.setOnClickListener(this);
        undoTv.setOnClickListener(this);
        switch (ep.getWatchStatus().getId()) {
            case WatchStatus.INIT:
                undoTv.setVisibility(GONE);
                break;
            case WatchStatus.WISH:
                wishTv.setVisibility(GONE);
                break;
            case WatchStatus.WATCHED:
                watchedTv.setVisibility(GONE);
                break;
            case WatchStatus.DROP:
                dropTv.setVisibility(GONE);
                break;
        }
        setContentView(view);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public interface onBottomClickListener {
        void onClick(View view);
    }
}
