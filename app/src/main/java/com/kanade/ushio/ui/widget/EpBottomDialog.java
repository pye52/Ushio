package com.kanade.ushio.ui.widget;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.TextView;

import com.kanade.ushio.R;
import com.kanade.ushio.entity.Ep;
import com.kanade.ushio.entity.WatchStatus;

import static android.view.View.GONE;
import static com.kanade.ushio.entity.WatchStatus.DROP_STR;
import static com.kanade.ushio.entity.WatchStatus.QUEUE_STR;
import static com.kanade.ushio.entity.WatchStatus.REMOVE_STR;
import static com.kanade.ushio.entity.WatchStatus.WATCHED_STR;
import static com.kanade.ushio.utils.GlobalKt.strFilter;

public class EpBottomDialog extends BottomSheetDialog implements View.OnClickListener {

    private onBottomClickListener listener;
    private Ep ep;
    private TextView title;
    private TextView airInfo;
    private TextView wishTv;
    private TextView watchedTv;
    private TextView dropTv;
    private TextView watchedHereTv;
    private TextView removeTv;
    /**
     * @param ep 传入要展示的集数
     */
    public EpBottomDialog(Context context, Ep ep, onBottomClickListener listener) {
        super(context);
        this.listener = listener;
        View view = getLayoutInflater().inflate(R.layout.bottomsheet_progress, null);

        title = view.findViewById(R.id.pw_ep_title);
        airInfo = view.findViewById(R.id.pw_air_info);
        wishTv =  view.findViewById(R.id.pw_ep_queue);
        watchedTv = view.findViewById(R.id.pw_ep_watched);
        dropTv = view.findViewById(R.id.pw_ep_drop);
        watchedHereTv = view.findViewById(R.id.pw_ep_watched_here);
        removeTv = view.findViewById(R.id.pw_ep_remove);

        setEp(ep);
        setContentView(view);
    }

    public void setEp(Ep ep) {
        this.ep = ep;
        this.wishTv.setVisibility(View.VISIBLE);
        this.watchedTv.setVisibility(View.VISIBLE);
        this.dropTv.setVisibility(View.VISIBLE);
        this.watchedHereTv.setVisibility(View.VISIBLE);
        this.removeTv.setVisibility(View.VISIBLE);
        initView(ep);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            switch (v.getId()) {
                case R.id.pw_ep_queue:
                    listener.onClick(ep, QUEUE_STR);
                    break;
                case R.id.pw_ep_watched:
                    listener.onClick(ep, WATCHED_STR);
                    break;
                case R.id.pw_ep_drop:
                    listener.onClick(ep, DROP_STR);
                    break;
                case R.id.pw_ep_watched_here:
                    listener.onClick(ep, null);
                    break;
                case R.id.pw_ep_remove:
                    listener.onClick(ep, REMOVE_STR);
                    break;
                default:
                    break;
            }
        }
    }

    public interface onBottomClickListener {
        void onClick(Ep ep, String statusStr);
    }

    private void initView(Ep ep) {
        String airInfoStr = getContext().getString(R.string.subject_progress_air_info, ep.getDuration(), ep.getAirdate(), ep.getComment());
        title.setText(strFilter(ep.getNameCn(), ep.getName(), 8));
        airInfo.setText(airInfoStr);
        wishTv.setOnClickListener(this);
        watchedTv.setOnClickListener(this);
        dropTv.setOnClickListener(this);
        watchedHereTv.setOnClickListener(this);
        removeTv.setOnClickListener(this);

        // 当剧集未有观看记录时，创建一个INIT状态
        if (ep.getWatchStatus() == null) {
            ep.setWatchStatus(WatchStatus.create(ep.getId()));
        }
        switch (ep.getWatchStatus().getStatus().getId()) {
            case WatchStatus.INIT:
                removeTv.setVisibility(GONE);
                break;
            case WatchStatus.QUEUE:
                wishTv.setVisibility(GONE);
                break;
            case WatchStatus.WATCHED:
                watchedTv.setVisibility(GONE);
                break;
            case WatchStatus.DROP:
                dropTv.setVisibility(GONE);
                break;
            default:
                break;
        }
    }
}
