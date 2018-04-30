package com.kanade.ushio.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kanade.ushio.R
import com.kanade.ushio.arch.room.SubjectDao
import com.kanade.ushio.entity.UserCollection
import com.kanade.ushio.utils.GlideApp
import com.kanade.ushio.utils.IO2MainThread

class UserCollectionAdapter(private var dao: SubjectDao, list: List<UserCollection>)
    : BaseQuickAdapter<UserCollection, BaseViewHolder>(R.layout.rv_item_user_collection, list) {
    override fun convert(helper: BaseViewHolder, item: UserCollection) {
        // 尝试从数据库搜索对应的subject
        // 当数据库中没有对应项应展示默认图片
        dao.querySubject(item.id)
                .IO2MainThread()
                .subscribe { subject ->
                    helper.setText(R.id.tv_main, subject.nameCn)
                            .setText(R.id.tv_sub, mContext.getString(R.string.tv_sub, subject.name, subject.airDate))
                            .addOnClickListener(R.id.tv_progress)

                    val img = helper.getView<ImageView>(R.id.img)
                    GlideApp.with(mContext)
                            .load(subject.images?.getImageL2S())
                            .placeholder(R.drawable.img_on_load)
                            .error(R.drawable.img_on_error)
                            .into(img)
                }
    }
}