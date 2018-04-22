package com.kanade.ushio.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Calendar(
        @PrimaryKey(autoGenerate = true)
        var id: Long,
        var weekday: Weekday,
        @SerializedName("items")
        var subjects: List<CalendarSubject>,
        var updateTime: Long = 0
)