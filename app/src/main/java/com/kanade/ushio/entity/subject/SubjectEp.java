package com.kanade.ushio.entity.subject;


import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kanade.ushio.entity.Ep;
import com.kanade.ushio.entity.typeadapter.SubjectEpTypeAdapter;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
@JsonAdapter(SubjectEpTypeAdapter.class)
public class SubjectEp implements RealmModel{
    @PrimaryKey
    private int id;
    private String url;
    private int type;
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
    private RealmList<Ep> eps;
    private RealmList<Crt> crt;
    private RealmList<Staff> staff;
    private RealmList<Topic> topic;
    private RealmList<Blog> blog;

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

    public RealmList<Ep> getEps() {
        return eps;
    }

    public void setEps(RealmList<Ep> eps) {
        this.eps = eps;
    }

    public RealmList<Crt> getCrt() {
        return crt;
    }

    public void setCrt(RealmList<Crt> crt) {
        this.crt = crt;
    }

    public RealmList<Staff> getStaff() {
        return staff;
    }

    public void setStaff(RealmList<Staff> staff) {
        this.staff = staff;
    }

    public RealmList<Topic> getTopic() {
        return topic;
    }

    public void setTopic(RealmList<Topic> topic) {
        this.topic = topic;
    }

    public RealmList<Blog> getBlog() {
        return blog;
    }

    public void setBlog(RealmList<Blog> blog) {
        this.blog = blog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubjectEp)) return false;

        SubjectEp subjectEp = (SubjectEp) o;

        if (id != subjectEp.id) return false;
        if (type != subjectEp.type) return false;
        if (airWeekday != subjectEp.airWeekday) return false;
        if (rank != subjectEp.rank) return false;
        if (url != null ? !url.equals(subjectEp.url) : subjectEp.url != null) return false;
        if (name != null ? !name.equals(subjectEp.name) : subjectEp.name != null) return false;
        if (nameCn != null ? !nameCn.equals(subjectEp.nameCn) : subjectEp.nameCn != null)
            return false;
        if (summary != null ? !summary.equals(subjectEp.summary) : subjectEp.summary != null)
            return false;
        if (airDate != null ? !airDate.equals(subjectEp.airDate) : subjectEp.airDate != null)
            return false;
        if (rating != null ? !rating.equals(subjectEp.rating) : subjectEp.rating != null)
            return false;
        if (images != null ? !images.equals(subjectEp.images) : subjectEp.images != null)
            return false;
        if (collection != null ? !collection.equals(subjectEp.collection) : subjectEp.collection != null)
            return false;
        if (eps != null ? !eps.equals(subjectEp.eps) : subjectEp.eps != null) return false;
        if (crt != null ? !crt.equals(subjectEp.crt) : subjectEp.crt != null) return false;
        if (staff != null ? !staff.equals(subjectEp.staff) : subjectEp.staff != null) return false;
        if (topic != null ? !topic.equals(subjectEp.topic) : subjectEp.topic != null) return false;
        if (blog != null ? !blog.equals(subjectEp.blog) : subjectEp.blog != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + type;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (nameCn != null ? nameCn.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (airDate != null ? airDate.hashCode() : 0);
        result = 31 * result + airWeekday;
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + rank;
        result = 31 * result + (images != null ? images.hashCode() : 0);
        result = 31 * result + (collection != null ? collection.hashCode() : 0);
        result = 31 * result + (eps != null ? eps.hashCode() : 0);
        result = 31 * result + (crt != null ? crt.hashCode() : 0);
        result = 31 * result + (staff != null ? staff.hashCode() : 0);
        result = 31 * result + (topic != null ? topic.hashCode() : 0);
        result = 31 * result + (blog != null ? blog.hashCode() : 0);
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
