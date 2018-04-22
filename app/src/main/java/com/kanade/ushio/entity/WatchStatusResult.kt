package com.kanade.ushio.entity

import com.google.gson.annotations.SerializedName

data class WatchStatusResult(
        @SerializedName("subject_id")
        var subjectId: Long,
        var eps: List<WatchStatus>
)