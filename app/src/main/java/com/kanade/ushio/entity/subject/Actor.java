package com.kanade.ushio.entity.subject;

import com.google.gson.annotations.JsonAdapter;
import com.kanade.ushio.entity.typeadapter.ActorTypeAdapter;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
@JsonAdapter(ActorTypeAdapter.class)
public class Actor implements RealmModel {
    @PrimaryKey
    private int id;
    private String url;
    private String name;
    private Image images;

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

    public Image getImages() {
        return images;
    }

    public void setImages(Image images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actor)) return false;

        Actor actor = (Actor) o;

        if (id != actor.id) return false;
        if (url != null ? !url.equals(actor.url) : actor.url != null) return false;
        if (name != null ? !name.equals(actor.name) : actor.name != null) return false;
        return images != null ? images.equals(actor.images) : actor.images == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        return result;
    }
}
