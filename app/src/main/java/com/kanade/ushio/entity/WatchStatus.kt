package com.kanade.ushio.entity

import com.google.gson.annotations.SerializedName

data class Status (
        // 1 => 想看 2 => 看过 3 => 抛弃
        var id: Int = 0,
        // url_name首字母大写
        @SerializedName("css_name")
        var cssName: String? = "",
        @SerializedName("url_name")
        var urlName: String? = "",
        // 参考id注释中文
        @SerializedName("cn_name")
        var cnName: String? = ""
)

data class WatchStatus(
        // 剧集id
        @SerializedName("id")
        var epId: Long = 0,
        var status: Status? = null
) {
    companion object {
        const val INIT = 0
        const val QUEUE = 1
        const val WATCHED = 2
        const val DROP = 3

        const val QUEUE_STR = "queue"
        const val WATCHED_STR = "watched"
        const val DROP_STR = "drop"
        const val REMOVE_STR = "remove"

        @JvmStatic
        fun create(epId: Long): WatchStatus {
            val watchStatus = WatchStatus()
            watchStatus.epId = epId
            watchStatus.status = Status(INIT)
            return watchStatus
        }
    }

    fun setStatus(statusStr: String) {
        when (statusStr) {
            REMOVE_STR -> {
                this.status?.id = INIT
                this.status?.cssName = ""
                this.status?.urlName = ""
                this.status?.cnName = ""
            }
            QUEUE_STR -> {
                this.status?.id = QUEUE
                this.status?.cssName = "Queue"
                this.status?.urlName = QUEUE_STR
                this.status?.cnName = "想看"
            }
            WATCHED_STR -> {
                this.status?.id = WATCHED
                this.status?.cssName = "Watched"
                this.status?.urlName = WATCHED_STR
                this.status?.cnName = "看过"
            }
            DROP_STR -> {
                this.status?.id = DROP
                this.status?.cssName = "Drop"
                this.status?.urlName = DROP_STR
                this.status?.cnName = "抛弃"
            }
        }
    }
}