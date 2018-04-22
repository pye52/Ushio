package com.kanade.ushio.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * 用户关注番剧展示数据类
 */
@Entity(foreignKeys = [
    (ForeignKey(entity = Subject::class,
            parentColumns = ["id"],
            childColumns = ["id"]))
])
data class UserCollection(
        @PrimaryKey
        @SerializedName("subject_id")
        var id: Long,
        var name: String,
        @SerializedName("ep_status")
        var epStatus: Int,
        @SerializedName("vol_status")
        var volStatus: Int,
        @SerializedName("lasttouch")
        var lastTouch: Long
) {
    @Ignore
    var subject: Subject? = null
}