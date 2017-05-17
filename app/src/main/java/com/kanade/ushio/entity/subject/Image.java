package com.kanade.ushio.entity.subject;


import android.text.TextUtils;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class Image implements RealmModel {
    private String large;
    private String common;
    private String medium;
    private String small;
    private String grid;

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = grid;
    }

    public String getImage() {
        if (!TextUtils.isEmpty(large)) return large;
        if (!TextUtils.isEmpty(common)) return common;
        if (!TextUtils.isEmpty(medium)) return medium;
        if (!TextUtils.isEmpty(small)) return small;
        if (!TextUtils.isEmpty(grid)) return grid;
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image)) return false;

        Image image = (Image) o;

        if (large != null ? !large.equals(image.large) : image.large != null) return false;
        if (common != null ? !common.equals(image.common) : image.common != null) return false;
        if (medium != null ? !medium.equals(image.medium) : image.medium != null) return false;
        if (small != null ? !small.equals(image.small) : image.small != null) return false;
        return grid != null ? grid.equals(image.grid) : image.grid == null;

    }

    @Override
    public int hashCode() {
        int result = large != null ? large.hashCode() : 0;
        result = 31 * result + (common != null ? common.hashCode() : 0);
        result = 31 * result + (medium != null ? medium.hashCode() : 0);
        result = 31 * result + (small != null ? small.hashCode() : 0);
        result = 31 * result + (grid != null ? grid.hashCode() : 0);
        return result;
    }
}
