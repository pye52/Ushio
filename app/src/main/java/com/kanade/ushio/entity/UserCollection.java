package com.kanade.ushio.entity;

import com.google.gson.annotations.JsonAdapter;
import com.kanade.ushio.entity.typeadapter.UserCollectionTypeAdapter;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
@JsonAdapter(UserCollectionTypeAdapter.class)
public class UserCollection implements RealmModel {
    @PrimaryKey
    private int id = 0;
    private RealmList<AniCollection> collections;

    public UserCollection(){}

    public UserCollection(int id, RealmList<AniCollection> collections) {
        this.id = id;
        this.collections = collections;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<AniCollection> getCollections() {
        return collections;
    }

    public void setCollections(RealmList<AniCollection> collections) {
        this.collections = collections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCollection)) return false;

        UserCollection that = (UserCollection) o;

        if (id != that.id) return false;
        return collections != null ? collections.equals(that.collections) : that.collections == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (collections != null ? collections.hashCode() : 0);
        return result;
    }
}
