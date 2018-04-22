package com.kanade.ushio.entity

import com.google.gson.annotations.SerializedName

data class SubjectCollection(
        @SerializedName("status")
        var status: CollectionStatus,
        var rating: Int,
        var comment: String,
        var private: Int,
        var tag: List<String>,
        @SerializedName("ep_status")
        var epStatus: Int,
        var lasttouch: Long
)

data class CollectionStatus(
        var id: Int,
        var type: String,
        var name: String
)