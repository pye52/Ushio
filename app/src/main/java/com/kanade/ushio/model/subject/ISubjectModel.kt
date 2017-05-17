package com.kanade.ushio.model.subject

import com.kanade.ushio.api.responses.GradeResponse
import com.kanade.ushio.entity.subject.SubjectEp
import com.kanade.ushio.model.IBaseModel
import io.reactivex.Observable

interface ISubjectModel : IBaseModel {
    /**
     * 注意已在方法内切换了线程
     */
    fun getSubject(subjectId: Int): Observable<SubjectEp?>

    fun getSubjectEpFromServer(subjectId: Int): Observable<SubjectEp?>

    fun getSubjectEpFromLocal(subjectId: Int): Observable<SubjectEp?>

    fun getSubjectEpGrade(subjectId: Int, auth: String): Observable<GradeResponse>

    fun updateGrade(subjectId: Int, status: String, rating: Int, comment: String, auth: String): Observable<GradeResponse>
}