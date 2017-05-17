package com.kanade.ushio.entity;

import com.google.gson.annotations.JsonAdapter;
import com.kanade.ushio.entity.subject.SubjectSimple;
import com.kanade.ushio.entity.typeadapter.CalendarTypeAdapter;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
@JsonAdapter(CalendarTypeAdapter.class)
public class Calendar implements RealmModel{
    private Weekday weekday;
    private RealmList<SubjectSimple> items;

    public Weekday getWeekday() {
        return weekday;
    }

    public void setWeekday(Weekday weekday) {
        this.weekday = weekday;
    }

    public RealmList<SubjectSimple> getItems() {
        return items;
    }

    public void setItems(RealmList<SubjectSimple> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Calendar)) return false;

        Calendar calendar = (Calendar) o;

        if (weekday != null ? !weekday.equals(calendar.weekday) : calendar.weekday != null)
            return false;
        return items != null ? items.equals(calendar.items) : calendar.items == null;

    }

    @Override
    public int hashCode() {
        int result = weekday != null ? weekday.hashCode() : 0;
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }
}
