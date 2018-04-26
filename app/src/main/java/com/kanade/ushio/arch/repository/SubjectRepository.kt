package com.kanade.ushio.arch.repository

import com.kanade.ushio.api.SubjectService
import com.kanade.ushio.arch.AppDatabase
import com.kanade.ushio.entity.Calendar
import com.kanade.ushio.entity.Ep
import com.kanade.ushio.entity.LargeSubject
import com.kanade.ushio.entity.SubjectCollection
import io.reactivex.Flowable

class SubjectRepository(private var service: SubjectService,
                        private var crtRepo: CrtRepository,
                        private var epRepo: EpRepository) {
    fun querySubject(subjectId: Long): Flowable<LargeSubject> {
        return service.querySubject(subjectId)
                .doOnNext {
                    AppDatabase.getInstance().beginTransaction()
                    it.crt?.forEach {
                        crtRepo.insertCrt(it)
                    }
                    it.eps?.let { eps ->
                        eps.forEach { ep ->
                            ep.subjectId = it.id
                        }
                        epRepo.insertEps(eps)
                    }
                    AppDatabase.getInstance().setTransactionSuccessful()
                    AppDatabase.getInstance().endTransaction()
                }
    }

    fun querySubjectEps(subjectId: Long): Flowable<List<Ep>> {
        return service.querySubjectEps(subjectId)
                .doOnNext {
                    AppDatabase.getInstance().beginTransaction()
                    it.eps?.let { eps ->
                        eps.forEach { ep ->
                            ep.subjectId = it.id
                        }
                        epRepo.insertEps(eps)
                    }
                    AppDatabase.getInstance().setTransactionSuccessful()
                    AppDatabase.getInstance().endTransaction()
                }
                .map { it.eps }
    }

    fun querySubjectCollection(subjectId: Long): Flowable<SubjectCollection> {
        return service.querySubjectCollection(subjectId)
    }

    fun updateSubject(subjectId: Long, status: String, comment: String, tags: String, rating: Int, privacy: Int): Flowable<SubjectCollection> {
        return service.updateSubject(subjectId, status, comment, tags, rating, privacy)
    }

    fun queryCalendarSubject(): Flowable<List<Calendar>> {
        return service.queryCalendar()
                .doOnNext {

                }
    }
}