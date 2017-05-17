package com.kanade.ushio.ui.subject_main.adapter

import android.text.TextUtils
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kanade.ushio.R
import com.kanade.ushio.entity.subject.Crt
import com.kanade.ushio.utils.ImageLoader.ImageLoader
import com.kanade.ushio.utils.ImageLoader.ImageLoaderUtil
import com.kanade.ushio.utils.strFilter

class SubjectCrtAdapter (datas: List<Crt>) : BaseQuickAdapter<Crt, BaseViewHolder>(R.layout.rv_item_crt, datas) {
    override fun convert(helper: BaseViewHolder, item: Crt) {
        val info = StringBuilder("")
                .addString2Builder("", item.roleName, false)
                .addString2Builder("性别", item.info.gender, true)
                .addString2Builder("生日", item.info.birth, true)
                .addString2Builder("身高", item.info.height, true)
                .addString2Builder("体重", item.info.weight, true)
                .addString2Builder("BWH", item.info.bwh, true)

        val cvName = if (item.actors.isNotEmpty()) item.actors[0].name else ""

        val img = helper.setText(R.id.crt_name, strFilter(item.nameCn, item.name, 12))
                .setText(R.id.crt_info, info.toString())
                .setText(R.id.crt_cv_name, cvName)
                .getView<ImageView>(R.id.crt_img)
        if (item.images != null) {
            val loader = ImageLoader.Builder()
                    .url(item.images.image)
                    .imgView(img)
                    .build()
            ImageLoaderUtil.loadImage(mContext, loader)
        }
    }

    private fun StringBuilder.addString2Builder(pre: String, str: String, isAppendEnd: Boolean): StringBuilder {
        if (!TextUtils.isEmpty(str)) {
            if (isAppendEnd) this.append(" \\ ")
            if (!TextUtils.isEmpty(pre)) this.append("$pre ")
            this.append("$str ")
        }
        return this
    }
}