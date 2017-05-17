package com.kanade.ushio.entity.subject;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kanade.ushio.entity.typeadapter.CrtTypeAdapter;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
@JsonAdapter(CrtTypeAdapter.class)
public class Crt implements RealmModel {
    @PrimaryKey
    private int id;
    private String url;
    private String name;
    @SerializedName("name_cn")
    private String nameCn;
    @SerializedName("role_name")
    private String roleName;
    private Image images;
    private int comment;
    private int collects;
    private Info info;
    private RealmList<Actor> actors;

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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Image getImages() {
        return images;
    }

    public void setImages(Image images) {
        this.images = images;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getCollects() {
        return collects;
    }

    public void setCollects(int collects) {
        this.collects = collects;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public RealmList<Actor> getActors() {
        return actors;
    }

    public void setActors(RealmList<Actor> actors) {
        this.actors = actors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Crt)) return false;

        Crt crt = (Crt) o;

        if (id != crt.id) return false;
        if (comment != crt.comment) return false;
        if (collects != crt.collects) return false;
        if (url != null ? !url.equals(crt.url) : crt.url != null) return false;
        if (name != null ? !name.equals(crt.name) : crt.name != null) return false;
        if (nameCn != null ? !nameCn.equals(crt.nameCn) : crt.nameCn != null) return false;
        if (roleName != null ? !roleName.equals(crt.roleName) : crt.roleName != null) return false;
        if (images != null ? !images.equals(crt.images) : crt.images != null) return false;
        if (info != null ? !info.equals(crt.info) : crt.info != null) return false;
        return actors != null ? actors.equals(crt.actors) : crt.actors == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (nameCn != null ? nameCn.hashCode() : 0);
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        result = 31 * result + comment;
        result = 31 * result + collects;
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (actors != null ? actors.hashCode() : 0);
        return result;
    }
}
