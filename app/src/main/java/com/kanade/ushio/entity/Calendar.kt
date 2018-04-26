package com.kanade.ushio.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Calendar(
        @PrimaryKey(autoGenerate = true)
        @SerializedName("id")
        var calendarId: Long = 0,
        @Embedded
        var weekday: Weekday? = null,
        @Ignore
        @SerializedName("items")
        var subjects: List<CalendarSubject>? = null,
        var updateTime: Long = 0
)