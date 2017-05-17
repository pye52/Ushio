package com.kanade.ushio.ui.subject_main.adapter

import android.text.TextUtils
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kanade.ushio.R
import com.kanade.ushio.entity.subject.Crt
import com.kanade.ushio.utils.ImageLoader.ImageLoader
import com.kanade.ushio.utils.ImageLoader.ImageLoaderUtil

class SubjectCrtSimpleAdapter(datas: List<Crt>) : BaseQuickAdapter<Crt, BaseViewHolder>(R.layout.rv_item_subject_detail, datas) {
    override fun convert(helper: BaseViewHolder, item: Crt) {
        var name = if (TextUtils.isEmpty(item.nameCn)) item.name else item.nameCn
        if (name.length > 5) name = "${name.substring(1, 5)}..."
        val img = helper.setText(R.id.detail_main, name)
                .setText(R.id.detail_sub, item.roleName)
                .getView<ImageView>(R.id.detail_img)
        val loader = ImageLoader.Builder()
                .url(item.images.image)
                .imgView(img)
                .build()
        ImageLoaderUtil.loadImage(mContext, loader)
    }
}