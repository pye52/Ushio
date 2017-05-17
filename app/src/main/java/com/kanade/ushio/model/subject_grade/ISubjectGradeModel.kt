package com.kanade.ushio.model.subject_grade

import com.kanade.ushio.api.responses.GradeResponse
import com.kanade.ushio.model.IBaseModel
import io.reactivex.Observable

interface ISubjectGradeModel : IBaseModel {
    fun getGrade(subjectId: Int, auth: String): Observable<GradeResponse>
}