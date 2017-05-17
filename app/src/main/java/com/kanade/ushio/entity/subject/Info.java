package com.kanade.ushio.entity.subject;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kanade.ushio.entity.typeadapter.InfoTypeAdapter;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
@JsonAdapter(InfoTypeAdapter.class)
public class Info implements RealmModel {
    @SerializedName("name_cn")
    private String nameCn = "";
    private Alias alias;
    private String gender = "";
    private String birth = "";
    private String height = "";
    private String weight = "";
    private String bwh = "";
    private String bloodtype = "";
    private RealmList<RealmString> source;

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public Alias getAlias() {
        return alias;
    }

    public void setAlias(Alias alias) {
        this.alias = alias;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBwh() {
        return bwh;
    }

    public void setBwh(String bwh) {
        this.bwh = bwh;
    }

    public String getBloodtype() {
        return bloodtype;
    }

    public void setBloodtype(String bloodtype) {
        this.bloodtype = bloodtype;
    }

    public RealmList<RealmString> getSource() {
        return source;
    }

    public void setSource(RealmList<RealmString> source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Info)) return false;

        Info info = (Info) o;

        if (nameCn != null ? !nameCn.equals(info.nameCn) : info.nameCn != null) return false;
        if (alias != null ? !alias.equals(info.alias) : info.alias != null) return false;
        if (gender != null ? !gender.equals(info.gender) : info.gender != null) return false;
        if (birth != null ? !birth.equals(info.birth) : info.birth != null) return false;
        if (height != null ? !height.equals(info.height) : info.height != null) return false;
        if (weight != null ? !weight.equals(info.weight) : info.weight != null) return false;
        if (bwh != null ? !bwh.equals(info.bwh) : info.bwh != null) return false;
        if (bloodtype != null ? !bloodtype.equals(info.bloodtype) : info.bloodtype != null)
            return false;
        return source != null ? source.equals(info.source) : info.source == null;

    }

    @Override
    public int hashCode() {
        int result = nameCn != null ? nameCn.hashCode() : 0;
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (birth != null ? birth.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (bwh != null ? bwh.hashCode() : 0);
        result = 31 * result + (bloodtype != null ? bloodtype.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        return result;
    }
}
