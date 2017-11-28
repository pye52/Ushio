package com.kanade.ushio.ui.subject_main.adapter

import android.text.TextUtils
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kanade.ushio.R
import com.kanade.ushio.entity.subject.Staff
import com.kanade.ushio.utils.strFilter

class SubjectStaffAdapter(datas: List<Staff>) : BaseQuickAdapter<Staff, BaseViewHolder>(R.layout.rv_item_crt, datas) {
    override fun convert(helper: BaseViewHolder, item: Staff) {
        val info = StringBuilder("").addString2Builder("", item.roleName, false)
        item.jobs.forEach { info.addString2Builder("", it.realmString, false) }
        info.addString2Builder("性别", item.info.gender, true)
                .addString2Builder("生日", item.info.birth, true)

        val img = helper.setText(R.id.crt_name, strFilter(item.nameCn, item.name, 12))
                .setText(R.id.crt_info, info.toString())
                .setVisible(R.id.crt_cv_name, false)
                .getView<ImageView>(R.id.crt_img)
        if (item.images != null) {
//            val loader = ImageLoader.Builder()
//                    .url(item.images.image)
//                    .placeHolder(R.drawable.ic_social_person)
//                    .imgView(img)
//                    .build()
//            ImageLoaderUtil.loadImage(mContext, loader)
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