package com.kanade.ushio.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kanade.ushio.R
import com.kanade.ushio.entity.CalendarSection
import com.kanade.ushio.utils.GlideApp
import com.kanade.ushio.utils.strFilter

class CalendarAdapter(datas: List<CalendarSection>)
    : BaseSectionQuickAdapter<CalendarSection, BaseViewHolder>(R.layout.rv_item_calendar, R.layout.rv_header_calendar, datas) {
    override fun convert(helper: BaseViewHolder, item: CalendarSection) {
        val rank = if (item.t.rank == 0) "???" else item.t.rank.toString()
        val calendarInfoStr = mContext.getString(R.string.calendar_info, item.t.rating.score.toString(), rank)
        val img = helper
                .setText(R.id.title, strFilter(item.t.nameCn, item.t.name, 8))
                .setText(R.id.rank, calendarInfoStr)
                .getView<ImageView>(R.id.img)
        GlideApp.with(mContext)
                .load(item.t.images.getImageL2S())
                .placeholder(R.drawable.img_on_load)
                .error(R.drawable.img_on_error)
                .into(img)
    }

    override fun convertHead(helper: BaseViewHolder, item: CalendarSection) {
        helper.setText(R.id.calendar_header, item.header)
    }
}