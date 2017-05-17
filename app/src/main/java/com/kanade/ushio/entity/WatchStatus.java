package com.kanade.ushio.entity;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kanade.ushio.entity.typeadapter.WatchStatusTypeAdapter;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
@JsonAdapter(WatchStatusTypeAdapter.class)
public class WatchStatus implements RealmModel{
    public static final int INIT = 0;
    public static final int WISH = 1;
    public static final int WATCHED = 2;
    public static final int DROP = 3;

    public static final String WISH_STR = "queue";
    public static final String WATCHED_STR = "watched";
    public static final String DROP_STR = "drop";
    public static final String UNDO_STR = "remove";

    public static WatchStatus create(int epId) {
        WatchStatus status = new WatchStatus();
        status.epId = epId;
        status.id = INIT;
        return status;
    }

    // 剧集id
    @PrimaryKey
    private int epId;
    // 1 => 想看 2 => 看过 3 => 抛弃
    private int id;
    // url_name首字母大写
    @SerializedName("css_name")
    private String cssName = "";
    @SerializedName("url_name")
    private String urlName = "";
    // 参考id注释中文
    @SerializedName("cn_name")
    private String cnName = "";

    public void setStatus(String statusStr) {
        switch (statusStr) {
            case UNDO_STR:
                this.id = INIT;
                this.cssName = "";
                this.urlName = "";
                this.cnName = "";
                break;
            case WISH_STR:
                this.id = WISH;
                this.cssName = "Queue";
                this.urlName = WISH_STR;
                this.cnName = "想看";
                break;
            case WATCHED_STR:
                this.id = WATCHED;
                this.cssName = "Watched";
                this.urlName = WATCHED_STR;
                this.cnName = "看过";
                break;
            case DROP_STR:
                this.id = DROP;
                this.cssName = "Drop";
                this.urlName = DROP_STR;
                this.cnName = "抛弃";
                break;
        }
    }

    public int getEpId() {
        return epId;
    }

    public void setEpId(int epId) {
        this.epId = epId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCssName() {
        return cssName;
    }

    public void setCssName(String cssName) {
        this.cssName = cssName;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WatchStatus)) return false;

        WatchStatus status = (WatchStatus) o;

        if (epId != status.epId) return false;
        if (id != status.id) return false;
        if (cssName != null ? !cssName.equals(status.cssName) : status.cssName != null)
            return false;
        if (urlName != null ? !urlName.equals(status.urlName) : status.urlName != null)
            return false;
        if (cnName != null ? !cnName.equals(status.cnName) : status.cnName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = epId;
        result = 31 * result + id;
        result = 31 * result + (cssName != null ? cssName.hashCode() : 0);
        result = 31 * result + (urlName != null ? urlName.hashCode() : 0);
        result = 31 * result + (cnName != null ? cnName.hashCode() : 0);
        return result;
    }
}
