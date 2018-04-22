package com.kanade.ushio.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * 每日连载页面的番剧数据类
 */
@Entity
data class CalendarSubject(
        @PrimaryKey
        var id: Long,
        var url: String,
        var type: Int,
        var name: String,
        @SerializedName("name_cn")
        var nameCn: String,
        var summary: String,
        @SerializedName("air_date")
        var airDate: String,
        @SerializedName("air_weekday")
        var airWeekday: Int,
        @Embedded
        var rating: Rating,
        var rank: Int,
        var collection: Collection,
        @Embedded
        var images: Image
)

data class Collection(
        var doing: Int
)