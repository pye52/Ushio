package com.kanade.ushio.adapter

import android.text.TextUtils
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kanade.ushio.R
import com.kanade.ushio.entity.Crt
import com.kanade.ushio.utils.GlideApp

class SubjectDetailCrtAdapter(datas: List<Crt>) : BaseQuickAdapter<Crt, BaseViewHolder>(R.layout.rv_item_subject_detail, datas) {
    override fun convert(helper: BaseViewHolder, item: Crt) {
        var name = if (TextUtils.isEmpty(item.nameCn)) item.name else item.nameCn
        name?.let {
            if (it.length > 5) {
                name = "${it.substring(1, 5)}..."
            }
        }
        val img = helper.setText(R.id.detail_main, name)
                .setText(R.id.detail_sub, item.roleName)
                .getView<ImageView>(R.id.detail_img)
        GlideApp.with(mContext)
                .load(item.images?.getImageC2L())
                .into(img)
    }
}