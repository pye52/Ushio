package com.kanade.ushio.entity;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kanade.ushio.entity.typeadapter.EpTypeAdapter;

import io.realm.RealmModel;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
@JsonAdapter(EpTypeAdapter.class)
public class Ep implements RealmModel {
    public static final String AIR = "Air";
    public static final String NA = "NA";
    public static final String TODAY = "Today";

    @PrimaryKey
    private int id;
    private String url;
    private int type;
    private int sort;
    private String name;
    @SerializedName("name_cn")
    private String nameCn;
    private String duration;
    private String airdate;
    private int comment;
    private String desc;
    private String status;
    private WatchStatus watchStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAirdate() {
        return airdate;
    }

    public void setAirdate(String airdate) {
        this.airdate = airdate;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public WatchStatus getWatchStatus() {
        return watchStatus;
    }

    public void setWatchStatus(WatchStatus watchStatus) {
        this.watchStatus = watchStatus;
    }

    /**
     * 是否已放送
     *
     * @return true => 已放送, false => 未放送
     */
    public boolean isShow() {
        return !status.equals(NA);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ep)) return false;

        Ep ep = (Ep) o;

        if (id != ep.id) return false;
        if (type != ep.type) return false;
        if (sort != ep.sort) return false;
        if (comment != ep.comment) return false;
        if (url != null ? !url.equals(ep.url) : ep.url != null) return false;
        if (name != null ? !name.equals(ep.name) : ep.name != null) return false;
        if (nameCn != null ? !nameCn.equals(ep.nameCn) : ep.nameCn != null) return false;
        if (duration != null ? !duration.equals(ep.duration) : ep.duration != null) return false;
        if (airdate != null ? !airdate.equals(ep.airdate) : ep.airdate != null) return false;
        if (desc != null ? !desc.equals(ep.desc) : ep.desc != null) return false;
        if (status != null ? !status.equals(ep.status) : ep.status != null) return false;
        return watchStatus != null ? watchStatus.equals(ep.watchStatus) : ep.watchStatus == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + type;
        result = 31 * result + sort;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (nameCn != null ? nameCn.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (airdate != null ? airdate.hashCode() : 0);
        result = 31 * result + comment;
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (watchStatus != null ? watchStatus.hashCode() : 0);
        return result;
    }
}
