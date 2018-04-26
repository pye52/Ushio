package com.kanade.ushio.entity

import android.arch.persistence.room.*
import com.google.gson.annotations.SerializedName

/**
 * 每日放送页面的番剧数据类
 */
@Entity(foreignKeys = [
        (ForeignKey(entity = Calendar::class,
                parentColumns = ["calendarId"],
                childColumns = ["calendarId"]))
])
data class CalendarSubject(
        @PrimaryKey
        var id: Long = 0,
        var url: String = "",
        var type: Int = 0,
        var name: String = "",
        @SerializedName("name_cn")
        var nameCn: String = "",
        var summary: String = "",
        @SerializedName("air_date")
        var airDate: String = "",
        @SerializedName("air_weekday")
        var airWeekday: Int = 0,
        @Embedded
        var rating: Rating? = null,
        var rank: Int = 0,
        @Embedded
        var collection: Collection? = null,
        @Embedded
        var images: Image? = null,
        var calendarId: Long = 0,
        // 0 => 未追番 1 => 已追番
        var hold: Int
) {
    fun holdSubject() {
        hold = 1
    }

    fun dropSubject() {
        hold = 0
    }

    fun onHold(): Boolean {
        return hold == 1
    }
}

data class Collection(
        var doing: Int = 0
)