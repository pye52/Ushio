package com.kanade.ushio.entity.subject;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kanade.ushio.entity.typeadapter.CollectionTypeAdapter;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
@JsonAdapter(CollectionTypeAdapter.class)
public class Collection implements RealmModel {
    private int wish;
    private int collect;
    private int doing;
    @SerializedName("on_hold")
    private int onHold;
    private int dropped;

    public int getWish() {
        return wish;
    }

    public void setWish(int wish) {
        this.wish = wish;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public int getDoing() {
        return doing;
    }

    public void setDoing(int doing) {
        this.doing = doing;
    }

    public int getOnHold() {
        return onHold;
    }

    public void setOnHold(int onHold) {
        this.onHold = onHold;
    }

    public int getDropped() {
        return dropped;
    }

    public void setDropped(int dropped) {
        this.dropped = dropped;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Collection)) return false;

        Collection that = (Collection) o;

        if (wish != that.wish) return false;
        if (collect != that.collect) return false;
        if (doing != that.doing) return false;
        if (onHold != that.onHold) return false;
        return dropped == that.dropped;

    }

    @Override
    public int hashCode() {
        int result = wish;
        result = 31 * result + collect;
        result = 31 * result + doing;
        result = 31 * result + onHold;
        result = 31 * result + dropped;
        return result;
    }
}
