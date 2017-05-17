package com.kanade.ushio.model.subject_progress

import com.kanade.ushio.api.responses.BaseResponse
import com.kanade.ushio.entity.Ep
import com.kanade.ushio.model.IBaseModel
import io.reactivex.Observable

interface ISubjectProgressModel : IBaseModel {
    fun getProgress(userId: Int, subjectEpId: Int, auth: String): Observable<List<Ep>>

    fun updateProgress(auth: String, epId: Int, statusStr: String): Observable<BaseResponse>
}