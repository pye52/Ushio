package com.kanade.ushio.api.responses

import com.google.gson.annotations.SerializedName
import com.kanade.ushio.entity.User
import com.kanade.ushio.utils.NoArg
import java.util.*

@NoArg
data class GradeResponse(
        var status: Status,
        var rating: Int,
        var comment: String,
        var tag: List<String>,
        // 最后观看的集数序号(非id)
        @SerializedName("ep_status")
        var epStatus: Int,
        var lasttouch: Long,
        var user: User? = null
) {
    companion object {
        @JvmStatic
        fun create(): GradeResponse = GradeResponse(
                status = Status.create(),
                rating = 0,
                comment = "",
                tag = Collections.emptyList(),
                epStatus = 0,
                lasttouch = System.currentTimeMillis(),
                user = null
        )
    }

    data class Status(
            // 整个番组的观看状态
            // 1 = 想看
            // 2 = 看过
            // 3 = 在看
            // 4 = 搁置
            // 5 = 抛弃
            var id: Int,
            // 文字描述
            // do = 在看
            // on_hold = 搁置
            // dropped = 弃番
            // wish = 想看
            // collect = 看过
            var type: String,
            var name: String
    ) {
        companion object {
            @JvmStatic
            fun create(): Status = Status(id = 0, type = "", name = "")
        }
    }

    fun setStatus(name: String) {
        status.id = when (name) {
            "wish" -> 1
            "collect" -> 2
            "do" -> 3
            "on_hold" -> 4
            "dropped" -> 5
            else -> 0
        }
    }
}