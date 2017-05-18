package com.kanade.ushio.ui.subscribe;

import android.widget.ImageView;

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
        SubjectSimple subject = item.getSubjectSimple();

        helper.setText(R.id.sublist_main, subject.getNameCn())
                .setText(R.id.sublist_sub, mContext.getString(R.string.sublist_sub, subject.getName(), subject.getAirDate()))
                .addOnClickListener(R.id.sublist_progress);

        ImageView img = helper.getView(R.id.sublist_img);
        ImageLoader loader = new ImageLoader.Builder()
                .url(subject.getImages().getLarge())
                .placeHolder(R.drawable.img_on_load)
                .imgView(img)
                .build();
        ImageLoaderUtil.INSTANCE.loadImage(mContext, loader);
    }
}
