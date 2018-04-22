package com.kanade.ushio.entity

import android.arch.persistence.room.Embedded

data class Rating(
        var total: Int = 0,
        @Embedded
        var count: Count? = null,
        var score: Float = 0f
)