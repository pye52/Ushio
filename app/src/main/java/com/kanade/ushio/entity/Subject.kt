package com.kanade.ushio.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * 含简单信息的番剧数据类
 */
@Entity
data class Subject(
        @PrimaryKey
        var id: Long,
        var url: String,
        var type: Int,
        var name: String,
        @SerializedName("name_cn")
        var nameCn: String,
        var summary: String,
        var eps: Int,
        @SerializedName("eps_count")
        var epsCount: Int,
        @SerializedName("air_date")
        var airDate: String,
        @SerializedName("air_weekday")
        var airWeekday: Int,
        @Embedded
        var images: Image? = null,
        @Embedded
        var rating: Rating? = null,
        var rank: Int = 0,
        @Embedded
        var collection: Collection? = null,
        // 0 => 未追番 1 => 已追番
        var hold: Int = 0
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