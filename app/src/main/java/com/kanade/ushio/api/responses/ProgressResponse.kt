package com.kanade.ushio.api.responses

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.kanade.ushio.entity.WatchStatus
import java.util.*

data class ProgressResponse(
        // 对应番剧id
        @SerializedName("subject_id")
        var subjectId: Int,
        var eps: List<WatchResponse>
) {
    companion object {
        @JvmStatic
        fun create(): ProgressResponse = ProgressResponse(0, Collections.emptyList())
    }

    data class WatchResponse(
            var id: Int,
            var status: WatchStatus
    )
}