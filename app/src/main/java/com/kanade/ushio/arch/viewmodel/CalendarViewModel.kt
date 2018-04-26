//package com.kanade.ushio.arch.viewmodel
//
//import android.arch.lifecycle.ViewModel
//import com.kanade.ushio.api.SubjectService
//import com.kanade.ushio.api.UserCollectionService
//import com.kanade.ushio.arch.repository.CalendarRepository
//import com.kanade.ushio.entity.Calendar
//import com.kanade.ushio.entity.SubjectCollection
//import com.kanade.ushio.entity.UserCollection
//import com.kanade.ushio.utils.getUserId
//import io.reactivex.Flowable
//import io.reactivex.functions.Function
//import org.reactivestreams.Publisher
//import java.util.function.BiFunction
//
//class CalendarViewModel(private var service: SubjectService,
//                        private var userCollectionService: UserCollectionService,
//                        private var repository: CalendarRepository) : ViewModel() {
//    /**
//     * 优先从本地数据库查询每日连载信息
//     *
//     * 数据库保存的数据每3天更新一次
//     */
//    fun queryCalendar(): Flowable<List<Calendar>> {
//        return Flowable.concat(
//                repository.queryCalendar(),
//                queryCalendarFromServer())
//                .filter { it.isNotEmpty() }
//                .take(1)
//    }
//
//    fun queryCalendarFromServer(): Flowable<List<Calendar>> {
//
//        return service.queryCalendar()
//                .flatMap{ calendars ->
//                    val userId = getUserId()
//                    // 将所有番剧的id以,分割串联
//                    val ids = calendars.joinToString(",") {
//                        it.subjects?.joinToString(",") { it.id.toString() } ?: ""
//                    }
//
//                    return@flatMap Flowable.combineLatest(
//                            Flowable.just(calendars),
//                            userCollectionService.getUserCollectionByIds(userId.toString(), ids),
//                      BiFunction { calendars1: List<Calendar>, userCollections: List<UserCollection> -> calendars1 })
//
//                }
//    }
//
//    fun holdSubject(subjectId: Long): Flowable<SubjectCollection> {
//        return service.updateSubject(subjectId, "do", "", "", 0, 0)
//    }
//
//    fun dropSubject(subjectId: Long): Flowable<SubjectCollection> {
//        return service.updateSubject(subjectId, "", "", "", 0, 0)
//    }
//}