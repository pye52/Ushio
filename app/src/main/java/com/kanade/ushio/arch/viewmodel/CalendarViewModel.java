package com.kanade.ushio.arch.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.kanade.ushio.arch.repository.CalendarRepository;
import com.kanade.ushio.arch.repository.SubjectRepository;
import com.kanade.ushio.arch.repository.UserRepository;
import com.kanade.ushio.entity.Calendar;
import com.kanade.ushio.entity.Subject;
import com.kanade.ushio.entity.SubjectCollection;
import com.kanade.ushio.entity.UserCollection;
import com.kanade.ushio.utils.SharedPreferencesUtilsKt;


import java.util.HashSet;
import java.util.List;

import io.reactivex.Flowable;

public class CalendarViewModel extends ViewModel {
    private SubjectRepository subjectRepository;
    private UserRepository userRepository;
    private CalendarRepository calendarRepository;

    public CalendarViewModel(SubjectRepository subjectRepository,
                             UserRepository userRepository,
                             CalendarRepository calendarRepository) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
        this.calendarRepository = calendarRepository;
    }

    // 优先从本地获取用户收藏数据以及每日放送数据，若没有则从网络获取
    public Flowable<List<Calendar>> queryCalendar() {
        long userId = SharedPreferencesUtilsKt.getUserId();
        return Flowable.combineLatest(
                calendarRepository.queryCalendar(),
                userRepository.queryCollection(String.valueOf(userId)),
                this::subjectStatusSetting);
    }

    public Flowable<SubjectCollection> updateSubjectAction(final Subject subject) {
        if (subject.onHold()) {
            return subjectRepository.updateSubject(subject.getId(), "", "", "", 0, 0)
                    .doOnNext(subjectCollection -> {
                        LogUtils.e("执行了drop");
                        subject.dropSubject();
                        userRepository.deleteCollectionById(subject.getId());
                        subjectRepository.insertSubject(subject);
                    });
        } else {
            return subjectRepository.updateSubject(subject.getId(), "do", "", "", 0, 0)
                    .doOnNext(subjectCollection -> {
                        LogUtils.e("执行了hold");
                        subject.holdSubject();

                        subjectRepository.insertSubject(subject);
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
            for (Subject subject : calendar.getSubjects()) {
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
