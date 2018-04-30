package com.kanade.ushio.entity

import com.google.gson.annotations.SerializedName

data class Calendar(
        @SerializedName("id")
        var calendarId: Long = 0,
        var weekday: Weekday? = null,
        @SerializedName("items")
        var subjects: List<Subject>? = null
)