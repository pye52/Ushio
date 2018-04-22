package com.kanade.ushio.entity

import com.google.gson.annotations.SerializedName

/**
 * 含详细讯息的番剧数据类
 */
class LargeSubject(
        var id: Long,
        var url: String,
        var type: Int,
        var name: String,
        @SerializedName("name_cn")
        var nameCn: String,
        var summary: String,
        @SerializedName("eps_count")
        var epsCount: Int,
        @SerializedName("air_date")
        var airDate: String,
        @SerializedName("air_weekday")
        var airWeekday: Int,
        var images: Image,
        var crt: List<Crt>? = null,
        var staff: List<Staff>? = null
) {
    var eps: List<Ep>? = null
    var rating: Rating? = null
    var rank: Int = 0

    fun getTypeDetail(): String {
        when (type) {
            1 -> return "漫画/小说"
            2 -> return "动画/二次元番"
            3 -> return "音乐"
            4 -> return "游戏"
            6 -> return "三次元番"
            else -> return ""
        }
    }

    fun getAirWeekdayStr(): String {
        when (airWeekday) {
            1 -> return "星期一"
            2 -> return "星期二"
            3 -> return "星期三"
            4 -> return "星期四"
            5 -> return "星期五"
            6 -> return "星期六"
            7 -> return "星期日"
            else -> return ""
        }
    }
}