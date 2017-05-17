package com.kanade.ushio.ui.subscribe;

import android.graphics.PorterDuff;
import android.os.Build;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kanade.ushio.R;
import com.kanade.ushio.entity.subject.SubjectSimple;
import com.kanade.ushio.entity.AniCollection;
import com.kanade.ushio.utils.ImageLoader.ImageLoader;
import com.kanade.ushio.utils.ImageLoader.ImageLoaderUtil;

import java.util.List;

/**
 * "我的进度"页面adapter，卡片式展示
 * Created by kanade on 2017/2/13.
 */

public class SubscribeListAdapter extends BaseQuickAdapter<AniCollection, BaseViewHolder> {
    public SubscribeListAdapter(List<AniCollection> list) {
        super(R.layout.rv_item_sublist, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, AniCollection item) {
        int currentProgress = item.getEpStatus();
        SubjectSimple subject = item.getSubjectSimple();
        String maxProgress = subject.getEps() == 0 ? "??" : String.valueOf(subject.getEps());
        String name = TextUtils.isEmpty(subject.getNameCn()) ? subject.getName() : subject.getNameCn();
        String progressStr = currentProgress + "/" + maxProgress;

        helper.setText(R.id.sublist_title, name)
                .setText(R.id.sublist_progress_label, progressStr);

        ProgressBar progressBar = helper.getView(R.id.sublist_progress);
        progressBar.setMax(subject.getEps());
        progressBar.setProgress(item.getEpStatus());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            progressBar.getProgressDrawable().setColorFilter(mContext.getColor(R.color.progressbar_color), PorterDuff.Mode.SRC_IN);
        } else {
            progressBar.getProgressDrawable().setColorFilter(mContext.getResources().getColor(R.color.progressbar_color), PorterDuff.Mode.SRC_IN);
        }

        ImageView img = helper.getView(R.id.sublist_img);
        ImageLoader loader = new ImageLoader.Builder()
                .url(subject.getImages().getLarge())
                .placeHolder(R.drawable.img_on_load)
                .imgView(img)
                .build();
        ImageLoaderUtil.INSTANCE.loadImage(mContext, loader);
    }
}
