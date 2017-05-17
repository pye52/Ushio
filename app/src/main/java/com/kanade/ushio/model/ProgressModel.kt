package com.kanade.ushio.model

import com.kanade.ushio.api.ApiManager
import com.kanade.ushio.api.ProgressService
import com.kanade.ushio.api.responses.ProgressResponse
import com.kanade.ushio.entity.Ep
import com.kanade.ushio.entity.WatchStatus
import com.kanade.ushio.entity.subject.SubjectEp

class ProgressModel : IBaseModel {
    override fun start() = Unit
    override fun stop() = Unit

    // 注意当用户没有对该番剧的剧集有过任何历史操作，则返回为null
    fun getProgress(userId: Int, ep: SubjectEp, auth: String) =
            ApiManager.getRetrofit()
                    .create(ProgressService::class.java)
                    .getSubjectProgress(userId, auth, ep.id)
                    .map { response ->
                        val p = ep.eps
                        p.forEach { it.watchStatus = WatchStatus() }
                        addEps2SubjectEp(p, response.eps)
                        p
                    }

    /**
     * 将剧集观看信息填充到[SubjectEp.eps]的[Ep.watchStatus]中
     */
    private fun addEps2SubjectEp(eps: List<Ep>, wrs: List<ProgressResponse.WatchResponse>) {
        var n = 0
        var i = 0
        // 正序排序
        val teps = wrs.sortedBy { it.id }
        while (n < eps.size) {
            val tep = eps[n]
            // 匹配是否当前话
            if (i < teps.size && tep.id == teps[i].id) {
                tep.watchStatus = teps[i].status
                i++
            }
            n++
        }
    }
}