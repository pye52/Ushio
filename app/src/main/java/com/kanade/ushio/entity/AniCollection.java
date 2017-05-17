package com.kanade.ushio.entity;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kanade.ushio.entity.subject.SubjectSimple;
import com.kanade.ushio.entity.typeadapter.AniCollectionTypeAdapter;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
@JsonAdapter(AniCollectionTypeAdapter.class)
public class AniCollection implements RealmModel {
    private String name = "";
    @SerializedName("ep_status")
    private int epStatus = 0;
    private long lasttouch = 0;
    @SerializedName("subject")
    private SubjectSimple subjectSimple;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEpStatus() {
        return epStatus;
    }

    public void setEpStatus(int epStatus) {
        this.epStatus = epStatus;
    }

    public long getLasttouch() {
        return lasttouch;
    }

    public void setLasttouch(long lasttouch) {
        this.lasttouch = lasttouch;
    }

    public SubjectSimple getSubjectSimple() {
        return subjectSimple;
    }

    public void setSubjectSimple(SubjectSimple subjectSimple) {
        this.subjectSimple = subjectSimple;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AniCollection)) return false;

        AniCollection that = (AniCollection) o;

        if (epStatus != that.epStatus) return false;
        if (lasttouch != that.lasttouch) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return subjectSimple != null ? subjectSimple.equals(that.subjectSimple) : that.subjectSimple == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + epStatus;
        result = 31 * result + (int) (lasttouch ^ (lasttouch >>> 32));
        result = 31 * result + (subjectSimple != null ? subjectSimple.hashCode() : 0);
        return result;
    }
}
