package com.kanade.ushio.ui.calendar

import android.widget.ImageView
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kanade.ushio.R
import com.kanade.ushio.adapter.models.CalendarSection
import com.kanade.ushio.utils.strFilter

class CalendarAdapter(datas: List<CalendarSection>)
    : BaseSectionQuickAdapter<CalendarSection, BaseViewHolder>(R.layout.rv_item_calendar, R.layout.rv_header_subject_simple, datas) {
    override fun convert(helper: BaseViewHolder, item: CalendarSection) {
        val rank = if (item.t.rank == 0) "???" else item.t.rank.toString()
        val calendarInfoStr = mContext.getString(R.string.calendar_info, item.t.rating.score.toString(), rank)
        val img = helper
                .setText(R.id.calendar_title, strFilter(item.t.nameCn, item.t.name, 8))
                .setText(R.id.calendar_rank, calendarInfoStr)
                .getView<ImageView>(R.id.calendar_img)


//        val loader = ImageLoader.Builder()
//                .url(item.t.images.large)
//                .placeHolder(R.drawable.img_on_load)
//                .imgView(img)
//                .build()
//        ImageLoaderUtil.loadImage(mContext, loader)
    }

    override fun convertHead(helper: BaseViewHolder, item: CalendarSection) {
        helper.setText(R.id.subject_simple_header, item.header)
    }
}