package com.kanade.ushio.adapter

import android.graphics.Paint
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kanade.ushio.R
import com.kanade.ushio.entity.Ep
import com.kanade.ushio.entity.Ep.Companion.AIR
import com.kanade.ushio.entity.Status
import com.kanade.ushio.entity.WatchStatus
import com.kanade.ushio.entity.WatchStatus.Companion.DROP
import com.kanade.ushio.entity.WatchStatus.Companion.INIT
import com.kanade.ushio.entity.WatchStatus.Companion.QUEUE
import com.kanade.ushio.entity.WatchStatus.Companion.WATCHED
import com.kanade.ushio.entity.WatchStatus.Companion.WATCHED_STR

class SubjectProgressAdapter(list: List<Ep>) : BaseQuickAdapter<Ep, BaseViewHolder>(R.layout.rv_item_subject_ep, list) {
    override fun convert(helper: BaseViewHolder, item: Ep) {
        val sortTv = helper.getView<TextView>(R.id.subject_ep)

        val sort = item.sort.toString()
        sortTv.text = sort
        val colorInt: Int
        if (item.status == AIR) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                colorInt = mContext.getColor(android.R.color.white)
            } else {
                colorInt = mContext.resources.getColor(android.R.color.white)
            }
            sortTv.setTextColor(colorInt)

            when (item.watchStatus?.status?.id) {
                QUEUE -> {
                    sortTv.setBackgroundResource(R.color.primary_darker)
                    sortTv.paintFlags = sortTv.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
                WATCHED -> {
                    sortTv.setBackgroundResource(R.color.primary)
                    sortTv.paintFlags = sortTv.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
                DROP -> {
                    sortTv.setBackgroundResource(R.color.grey_500)
                    sortTv.paintFlags = sortTv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                else -> {
                    sortTv.setBackgroundResource(R.color.primary_light)
                    sortTv.paintFlags = sortTv.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                colorInt = mContext.getColor(R.color.text_primary)
            } else {
                colorInt = mContext.resources.getColor(R.color.text_primary)
            }
            sortTv.setTextColor(colorInt)
            sortTv.setBackgroundResource(R.color.grey_300)
        }
    }

    fun setWatchStatus(list: List<WatchStatus>) {
        var i = 0
        // 先按id顺序排序
        list.sortedBy { it.epId }
                .forEach { watchStatus ->
                    (i until mData.size).forEach {
                        // 若剧集id相同则更新其查看状态，同时记录当前位置
                        // 因为list已经是顺序排序了，没有必要再从前面开始遍历一次
                        if (mData[it].id == watchStatus.epId) {
                            mData[it].watchStatus = watchStatus
                            i = it + 1
                        }
                    }
                }
    }

    fun watchedHere(position: Int) {
        mData.forEachIndexed { index, ep ->
            if (index <= position) {
                if (ep.watchStatus == null) {
                    val watchStatus = WatchStatus(ep.id)
                    watchStatus.setStatus(WATCHED_STR)
                    ep.watchStatus = watchStatus
                } else {
                    ep.watchStatus?.setStatus(WATCHED_STR)
                }
            } else {
                if (ep.watchStatus == null) {
                    val watchStatus = WatchStatus(ep.id)
                    val status = Status()
                    watchStatus.status = status
                    ep.watchStatus = watchStatus
                } else {
                    ep.watchStatus?.setStatus(WatchStatus.REMOVE_STR)
                }
            }
        }
    }
}