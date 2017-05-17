package com.kanade.ushio.entity.subject;

import com.google.gson.annotations.JsonAdapter;
import com.kanade.ushio.entity.typeadapter.AliasTypeAdapter;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
@JsonAdapter(AliasTypeAdapter.class)
public class Alias implements RealmModel {
    private RealmList<RealmString> aliasString;
    private String _0 = "";
    private String _1 = "";
    private String _2 = "";
    private String _3 = "";
    private String _4 = "";
    private String _5 = "";
    private String zh = "";
    private String en = "";
    private String jp = "";
    private String kana = "";
    private String romaji = "";

    public RealmList<RealmString> getAliasString() {
        return aliasString;
    }

    public void setAliasString(RealmList<RealmString> aliasString) {
        this.aliasString = aliasString;
    }

    public String get_0() {
        return _0;
    }

    public void set_0(String _0) {
        this._0 = _0;
    }

    public String get_1() {
        return _1;
    }

    public void set_1(String _1) {
        this._1 = _1;
    }

    public String get_2() {
        return _2;
    }

    public void set_2(String _2) {
        this._2 = _2;
    }

    public String get_3() {
        return _3;
    }

    public void set_3(String _3) {
        this._3 = _3;
    }

    public String get_4() {
        return _4;
    }

    public void set_4(String _4) {
        this._4 = _4;
    }

    public String get_5() {
        return _5;
    }

    public void set_5(String _5) {
        this._5 = _5;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getJp() {
        return jp;
    }

    public void setJp(String jp) {
        this.jp = jp;
    }

    public String getKana() {
        return kana;
    }

    public void setKana(String kana) {
        this.kana = kana;
    }

    public String getRomaji() {
        return romaji;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alias)) return false;

        Alias alias = (Alias) o;

        if (aliasString != null ? !aliasString.equals(alias.aliasString) : alias.aliasString != null)
            return false;
        if (_0 != null ? !_0.equals(alias._0) : alias._0 != null) return false;
        if (_1 != null ? !_1.equals(alias._1) : alias._1 != null) return false;
        if (_2 != null ? !_2.equals(alias._2) : alias._2 != null) return false;
        if (_3 != null ? !_3.equals(alias._3) : alias._3 != null) return false;
        if (_4 != null ? !_4.equals(alias._4) : alias._4 != null) return false;
        if (_5 != null ? !_5.equals(alias._5) : alias._5 != null) return false;
        if (zh != null ? !zh.equals(alias.zh) : alias.zh != null) return false;
        if (en != null ? !en.equals(alias.en) : alias.en != null) return false;
        if (jp != null ? !jp.equals(alias.jp) : alias.jp != null) return false;
        if (kana != null ? !kana.equals(alias.kana) : alias.kana != null) return false;
        return romaji != null ? romaji.equals(alias.romaji) : alias.romaji == null;

    }

    @Override
    public int hashCode() {
        int result = aliasString != null ? aliasString.hashCode() : 0;
        result = 31 * result + (_0 != null ? _0.hashCode() : 0);
        result = 31 * result + (_1 != null ? _1.hashCode() : 0);
        result = 31 * result + (_2 != null ? _2.hashCode() : 0);
        result = 31 * result + (_3 != null ? _3.hashCode() : 0);
        result = 31 * result + (_4 != null ? _4.hashCode() : 0);
        result = 31 * result + (_5 != null ? _5.hashCode() : 0);
        result = 31 * result + (zh != null ? zh.hashCode() : 0);
        result = 31 * result + (en != null ? en.hashCode() : 0);
        result = 31 * result + (jp != null ? jp.hashCode() : 0);
        result = 31 * result + (kana != null ? kana.hashCode() : 0);
        result = 31 * result + (romaji != null ? romaji.hashCode() : 0);
        return result;
    }
}
