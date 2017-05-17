package com.kanade.ushio.entity.subject;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kanade.ushio.entity.User;
import com.kanade.ushio.entity.typeadapter.TopicTypeAdapter;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
@JsonAdapter(TopicTypeAdapter.class)
public class Topic implements RealmModel {
    @PrimaryKey
    private int id;
    private String url;
    private String title;
    @SerializedName("main_id")
    private int mainId;
    private long timestamp;
    private long lastpost;
    private int replies;
    private User user;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMainId() {
        return mainId;
    }

    public void setMainId(int mainId) {
        this.mainId = mainId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getLastpost() {
        return lastpost;
    }

    public void setLastpost(long lastpost) {
        this.lastpost = lastpost;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Topic)) return false;

        Topic topic = (Topic) o;

        if (id != topic.id) return false;
        if (mainId != topic.mainId) return false;
        if (timestamp != topic.timestamp) return false;
        if (lastpost != topic.lastpost) return false;
        if (replies != topic.replies) return false;
        if (url != null ? !url.equals(topic.url) : topic.url != null) return false;
        if (title != null ? !title.equals(topic.title) : topic.title != null) return false;
        return user != null ? user.equals(topic.user) : topic.user == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + mainId;
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + (int) (lastpost ^ (lastpost >>> 32));
        result = 31 * result + replies;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
