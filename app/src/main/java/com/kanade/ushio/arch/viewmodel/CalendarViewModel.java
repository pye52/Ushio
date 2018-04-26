package com.kanade.ushio.arch.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.kanade.ushio.api.SubjectService;
import com.kanade.ushio.api.UserCollectionService;
import com.kanade.ushio.arch.repository.CalendarRepository;
import com.kanade.ushio.arch.room.UserCollectionDao;
import com.kanade.ushio.entity.Calendar;
import com.kanade.ushio.entity.CalendarSubject;
import com.kanade.ushio.entity.SubjectCollection;
import com.kanade.ushio.entity.UserCollection;
import com.kanade.ushio.utils.SharedPreferencesUtilsKt;

import org.reactivestreams.Publisher;

import java.util.HashSet;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class CalendarViewModel extends ViewModel {
    private SubjectService subjectService;
    private UserCollectionDao userCollectionDao;
    private UserCollectionService userCollectionService;
    private CalendarRepository calendarRepository;

    public CalendarViewModel(SubjectService subjectService, UserCollectionDao userCollectionDao,
                             UserCollectionService userCollectionService, CalendarRepository calendarRepository) {
        this.subjectService = subjectService;
        this.userCollectionDao = userCollectionDao;
        this.userCollectionService = userCollectionService;
        this.calendarRepository = calendarRepository;
    }

    // 优先从本地获取用户收藏数据以及每日放送数据，若没有则从网络获取
    public Flowable<List<Calendar>> queryCalendar() {
        long userId = SharedPreferencesUtilsKt.getUserId();
        return Flowable.combineLatest(
                calendarRepository.queryCalendar()
                        .flatMap((Function<List<Calendar>, Publisher<List<Calendar>>>) list -> {
                            if (list.isEmpty()) {
                                return subjectService.queryCalendar()
                                        .doOnSubscribe(subscription -> calendarRepository.deleteAll())
                                        .doOnNext(calendars -> calendarRepository.insertCalendar(calendars));
                            } else {
                                return Flowable.just(list);
                            }
                        }),
                userCollectionDao.queryUserCollection()
                        .flatMap((Function<List<UserCollection>, Publisher<List<UserCollection>>>) list -> {
                            if (list.isEmpty()) {
                                return userCollectionService.getUserCollection(String.valueOf(userId), "watching", "small");
                            } else {
                                return Flowable.just(list);
                            }
                        }),
                this::subjectStatusSetting);
    }

    public Flowable<List<Calendar>> queryCalendarFromServer() {
        long userId = SharedPreferencesUtilsKt.getUserId();
        return Flowable.combineLatest(
                subjectService.queryCalendar(),
                userCollectionService.getUserCollection(String.valueOf(userId), "watching", "small"),
                this::subjectStatusSetting)
                // 每次获取新信息，都得先清理数据库
                .doOnSubscribe(subscription -> calendarRepository.deleteAll())
                .doOnNext(calendars -> calendarRepository.insertCalendar(calendars));
    }

    public Flowable<SubjectCollection> updateSubjectAction(final CalendarSubject subject) {
        if (subject.onHold()) {
            return subjectService.updateSubject(subject.getId(), "do", "", "", 0, 0)
                    .doOnNext(subjectCollection -> {
                        subject.dropSubject();
                        calendarRepository.updateCalendarSubject(subject);
                    });
        } else {
            return subjectService.updateSubject(subject.getId(), "", "", "", 0, 0)
                    .doOnNext(subjectCollection -> {
                        subject.holdSubject();
                        calendarRepository.updateCalendarSubject(subject);
                    });
        }
    }

    @NonNull
    private List<Calendar> subjectStatusSetting(List<Calendar> calendars, List<UserCollection> collections) {
        // 检查每日放送中是否已收藏列表
        HashSet<Long> set = new HashSet<>();
        for (UserCollection collection : collections) {
            set.add(collection.getId());
        }

        for (Calendar calendar : calendars) {
            if (calendar.getSubjects() == null) {
                continue;
            }
            for (CalendarSubject subject : calendar.getSubjects()) {
                if (set.contains(subject.getId())) {
                    subject.holdSubject();
                } else {
                    subject.dropSubject();
                }
            }
        }
        return calendars;
    }
}
