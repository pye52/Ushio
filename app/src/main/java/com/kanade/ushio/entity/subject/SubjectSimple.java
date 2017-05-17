package com.kanade.ushio.entity.subject;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * 由于realmobject不能继承自其他类
 *
 * 因此需要保持该类与{@link SubjectEp}基础属性相同
 */
@RealmClass
@JsonAdapter(SubjectSimple.class)
public class SubjectSimple implements RealmModel{
    @PrimaryKey
    private int id;
    private String url;
    private int type;
    private int eps;
    private String name;
    @SerializedName("name_cn")
    private String nameCn;
    private String summary;
    @SerializedName("air_date")
    private String airDate;
    @SerializedName("air_weekday")
    private int airWeekday;
    private Rating rating;
    private int rank;
    private Image images;
    private Collection collection;

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

    public int getEps() {
        return eps;
    }

    public void setEps(int eps) {
        this.eps = eps;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public int getAirWeekday() {
        return airWeekday;
    }

    public void setAirWeekday(int airWeekday) {
        this.airWeekday = airWeekday;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Image getImages() {
        return images;
    }

    public void setImages(Image images) {
        this.images = images;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubjectSimple)) return false;

        SubjectSimple that = (SubjectSimple) o;

        if (id != that.id) return false;
        if (type != that.type) return false;
        if (eps != that.eps) return false;
        if (airWeekday != that.airWeekday) return false;
        if (rank != that.rank) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (nameCn != null ? !nameCn.equals(that.nameCn) : that.nameCn != null) return false;
        if (summary != null ? !summary.equals(that.summary) : that.summary != null) return false;
        if (airDate != null ? !airDate.equals(that.airDate) : that.airDate != null) return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        if (images != null ? !images.equals(that.images) : that.images != null) return false;
        return collection != null ? collection.equals(that.collection) : that.collection == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + type;
        result = 31 * result + eps;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (nameCn != null ? nameCn.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (airDate != null ? airDate.hashCode() : 0);
        result = 31 * result + airWeekday;
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + rank;
        result = 31 * result + (images != null ? images.hashCode() : 0);
        result = 31 * result + (collection != null ? collection.hashCode() : 0);
        return result;
    }

    public String getTypeDetail() {
        switch (type) {
            case 1:
                return "漫画/小说";
            case 2:
                return "动画/二次元番";
            case 3:
                return "音乐";
            case 4:
                return "游戏";
            case 6:
                return "三次元番";
            default:
                return "";
        }
    }

    public String getAirWeekdayStr() {
        switch (airWeekday) {
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
            case 7:
                return "星期日";
            default:
                return "";
        }
    }
}
