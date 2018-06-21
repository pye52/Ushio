package com.kanade.ushio.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Ep(
        @PrimaryKey
        var id: Long = 0,
        var url: String? = null,
        var type: Int = 0,
        var sort: Float = 0f,
        var name: String? = null,
        @SerializedName("name_cn")
        var nameCn: String? = null,
        var duration: String? = null,
        var airdate: String? = null,
        var comment: Int = 0,
        var desc: String? = null,
        /** Tv播放状态，非用户观看进度 **/
        var status: String? = null,
        var subjectId: Long = 0
) {
    companion object {
        const val AIR = "Air"
        const val NA = "NA"
        const val TODAY = "Today"
    }

    @Ignore
    var watchStatus: WatchStatus? = null
}