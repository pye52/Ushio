package com.kanade.ushio.entity;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kanade.ushio.entity.subject.Image;
import com.kanade.ushio.entity.typeadapter.UserTypeAdapter;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
@JsonAdapter(UserTypeAdapter.class)
public class User implements RealmModel {
    @PrimaryKey
    private int id;
    private String url;
    private String username;
    private String nickname;
    private Image avatar;
    private String sign;
    private String auth;
    @SerializedName("auth_encode")
    private String authEncode;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getAuthEncode() {
        return authEncode;
    }

    public void setAuthEncode(String authEncode) {
        this.authEncode = authEncode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (url != null ? !url.equals(user.url) : user.url != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null)
            return false;
        if (nickname != null ? !nickname.equals(user.nickname) : user.nickname != null)
            return false;
        if (avatar != null ? !avatar.equals(user.avatar) : user.avatar != null) return false;
        if (sign != null ? !sign.equals(user.sign) : user.sign != null) return false;
        if (auth != null ? !auth.equals(user.auth) : user.auth != null) return false;
        return authEncode != null ? authEncode.equals(user.authEncode) : user.authEncode == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        result = 31 * result + (sign != null ? sign.hashCode() : 0);
        result = 31 * result + (auth != null ? auth.hashCode() : 0);
        result = 31 * result + (authEncode != null ? authEncode.hashCode() : 0);
        return result;
    }
}
