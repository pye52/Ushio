package com.kanade.ushio.adapter

import android.text.TextUtils
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kanade.ushio.R
import com.kanade.ushio.entity.Crt
import com.kanade.ushio.utils.GlideApp
import com.kanade.ushio.utils.strFilter

class SubjectCrtAdapter (datas: List<Crt>) : BaseQuickAdapter<Crt, BaseViewHolder>(R.layout.rv_item_subject_detail_crt, datas) {
    override fun convert(helper: BaseViewHolder, item: Crt) {
        val infoStr = StringBuilder("")
        item.info?.let { info ->
            infoStr.addString2Builder("", item.roleName, false)
                    .addString2Builder("性别", info.gender, true)
                    .addString2Builder("生日", info.birth, true)
                    .addString2Builder("身高", info.height, true)
                    .addString2Builder("体重", info.weight, true)
                    .addString2Builder("BWH", info.bwh, true)
        }


        // 若不存在cv的信息，则隐藏相关控件
        val cvImg = helper.getView<ImageView>(R.id.crt_cv_img)
        item.actors?.let { list ->
            if (list.isNotEmpty()) {
                GlideApp.with(mContext)
                        .load(list[0].images?.getImageC2L())
                        .placeholder(R.drawable.img_on_load)
                        .error(R.drawable.img_on_error)
                        .into(cvImg)
                helper.setVisible(R.id.crt_cv_name, true)
                        .setVisible(R.id.crt_cv_img, true)
                        .setText(R.id.crt_cv_name, list[0].name)
            } else {
                helper.setVisible(R.id.crt_cv_name, false)
                        .setVisible(R.id.crt_cv_img, false)
            }
        } ?: let {
            helper.setVisible(R.id.crt_cv_name, false)
                    .setVisible(R.id.crt_cv_img, false)
        }

        val img = helper.setText(R.id.crt_name, strFilter(item.nameCn, item.name, 12))
                .setText(R.id.crt_info, infoStr.toString())
                .getView<ImageView>(R.id.crt_img)
        if (item.images != null) {
            GlideApp.with(mContext)
                    .load(item.images?.getImageC2L())
                    .placeholder(R.drawable.img_on_load)
                    .error(R.drawable.img_on_error)
                    .into(img)
        }
    }

    private fun StringBuilder.addString2Builder(pre: String?, str: String?, isAppendEnd: Boolean): StringBuilder {
        if (!TextUtils.isEmpty(str)) {
            if (isAppendEnd) this.append(" \\ ")
            if (!TextUtils.isEmpty(pre)) this.append("$pre ")
            this.append("$str ")
        }
        return this
    }
}