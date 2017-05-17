package com.kanade.ushio.entity.subject;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class RealmString implements RealmModel {
    private String realmString;

    public RealmString(){}

    public RealmString(String s) {
        this.realmString = s;
    }

    public String getRealmString() {
        return realmString;
    }

    public void setRealmString(String realmString) {
        this.realmString = realmString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RealmString)) return false;

        RealmString that = (RealmString) o;

        return realmString != null ? realmString.equals(that.realmString) : that.realmString == null;

    }

    @Override
    public int hashCode() {
        return realmString != null ? realmString.hashCode() : 0;
    }
}
