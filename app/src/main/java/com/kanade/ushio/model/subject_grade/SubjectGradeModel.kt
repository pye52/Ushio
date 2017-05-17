package com.kanade.ushio.model.subject_grade

import com.kanade.ushio.api.ApiManager
import com.kanade.ushio.api.GradeService
import io.realm.Realm

class SubjectGradeModel : ISubjectGradeModel {
    private lateinit var realm: Realm

    override fun start() {
        realm = Realm.getDefaultInstance()
    }

    override fun stop() {
        realm.close()
    }

    override fun getGrade(subjectId: Int, auth: String) =
            ApiManager.getRetrofit()
                    .create(GradeService::class.java)
                    .getSubjectCollection(subjectId, auth)
}