package com.kanade.ushio.model.subject_crt

import com.kanade.ushio.entity.subject.Crt
import com.kanade.ushio.model.IBaseModel
import io.reactivex.Observable

interface ISubjectCrtModel : IBaseModel {
    fun getSubjectCrt(subjectEpId: Int): Observable<List<Crt>>
}