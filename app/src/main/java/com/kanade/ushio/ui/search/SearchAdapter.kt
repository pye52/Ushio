package com.kanade.ushio.ui.search

import android.text.TextUtils
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kanade.ushio.R
import com.kanade.ushio.entity.subject.SubjectSimple
import com.kanade.ushio.utils.ImageLoader.ImageLoader
import com.kanade.ushio.utils.ImageLoader.ImageLoaderUtil
import com.kanade.ushio.utils.strFilter
import com.kanade.ushio.utils.stringFormat

class SearchAdapter(datas: List<SubjectSimple>) : BaseQuickAdapter<SubjectSimple, BaseViewHolder>(R.layout.rv_item_search, datas) {
    private var key: String = ""

    fun setKey(key: String) {
        this.key = key
    }

    override fun convert(helper: BaseViewHolder, item: SubjectSimple) {
        val img = helper.getView<ImageView>(R.id.search_img)
        val loader = ImageLoader.Builder()
                .url(item.images.large)
                .placeHolder(R.drawable.img_on_load)
                .imgView(img)
                .build()
        ImageLoaderUtil.loadImage(mContext, loader)
        helper.setText(R.id.search_outline, item.airDate)
                .setText(R.id.search_type, "类别: ${item.typeDetail}")

        val title = strFilter(item.nameCn, item.name, 12)
        if (key.isNotEmpty()) {
            val titleSpan = stringFormat(title, key)
            helper.setText(R.id.search_title, titleSpan)
        } else {
            helper.setText(R.id.search_title, title)
        }
    }
}