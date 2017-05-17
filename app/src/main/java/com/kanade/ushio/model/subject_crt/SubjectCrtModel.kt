package com.kanade.ushio.model.subject_crt

import com.kanade.ushio.entity.subject.Crt
import com.kanade.ushio.model.subject.ISubjectModel
import com.kanade.ushio.model.subject.SubjectModel
import io.reactivex.Observable
import java.util.*

class SubjectCrtModel : ISubjectCrtModel {
    private lateinit var epModel: ISubjectModel

    override fun start() {
        epModel = SubjectModel()
        epModel.start()
    }

    override fun stop() {
        epModel.stop()
    }

    override fun getSubjectCrt(subjectEpId: Int): Observable<List<Crt>> =
            epModel.getSubjectEpFromLocal(subjectEpId)
                    .map { it?.crt ?: Collections.emptyList<Crt>() }
}