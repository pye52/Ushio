package com.kanade.ushio.entity;

import com.google.gson.annotations.JsonAdapter;
import com.kanade.ushio.entity.typeadapter.WeekdayTypeAdapter;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
@JsonAdapter(WeekdayTypeAdapter.class)
public class Weekday implements RealmModel{
    private String en = "";
    private String cn = "";
    private String ja = "";
    private String id = "";

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getJa() {
        return ja;
    }

    public void setJa(String ja) {
        this.ja = ja;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Weekday)) return false;

        Weekday weekday = (Weekday) o;

        if (en != null ? !en.equals(weekday.en) : weekday.en != null) return false;
        if (cn != null ? !cn.equals(weekday.cn) : weekday.cn != null) return false;
        if (ja != null ? !ja.equals(weekday.ja) : weekday.ja != null) return false;
        return id != null ? id.equals(weekday.id) : weekday.id == null;

    }

    @Override
    public int hashCode() {
        int result = en != null ? en.hashCode() : 0;
        result = 31 * result + (cn != null ? cn.hashCode() : 0);
        result = 31 * result + (ja != null ? ja.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
