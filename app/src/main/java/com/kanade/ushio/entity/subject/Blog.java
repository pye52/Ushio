package com.kanade.ushio.entity.subject;


import com.google.gson.annotations.JsonAdapter;
import com.kanade.ushio.entity.User;
import com.kanade.ushio.entity.typeadapter.BlogTypeAdapter;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
@JsonAdapter(BlogTypeAdapter.class)
public class Blog implements RealmModel {
    @PrimaryKey
    private int id;
    private String url = "";
    private String title = "";
    private String summary = "";
    // TODO 暂时忽略评论的图片属性
    private int replies;
    private long timestamp;
    private String dateline = "";
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
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
        if (!(o instanceof Blog)) return false;

        Blog blog = (Blog) o;

        if (id != blog.id) return false;
        if (replies != blog.replies) return false;
        if (timestamp != blog.timestamp) return false;
        if (url != null ? !url.equals(blog.url) : blog.url != null) return false;
        if (title != null ? !title.equals(blog.title) : blog.title != null) return false;
        if (summary != null ? !summary.equals(blog.summary) : blog.summary != null) return false;
        if (dateline != null ? !dateline.equals(blog.dateline) : blog.dateline != null)
            return false;
        return user != null ? user.equals(blog.user) : blog.user == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + replies;
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + (dateline != null ? dateline.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
