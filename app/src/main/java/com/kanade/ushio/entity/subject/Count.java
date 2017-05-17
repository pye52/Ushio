package com.kanade.ushio.entity.subject;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kanade.ushio.entity.typeadapter.CountTypeAdapter;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
@JsonAdapter(CountTypeAdapter.class)
public class Count implements RealmModel {
    @SerializedName("1")
    private int _1;
    @SerializedName("2")
    private int _2;
    @SerializedName("3")
    private int _3;
    @SerializedName("4")
    private int _4;
    @SerializedName("5")
    private int _5;
    @SerializedName("6")
    private int _6;
    @SerializedName("7")
    private int _7;
    @SerializedName("8")
    private int _8;
    @SerializedName("9")
    private int _9;
    @SerializedName("10")
    private int _10;

    public int get_1() {
        return _1;
    }

    public void set_1(int _1) {
        this._1 = _1;
    }

    public int get_2() {
        return _2;
    }

    public void set_2(int _2) {
        this._2 = _2;
    }

    public int get_3() {
        return _3;
    }

    public void set_3(int _3) {
        this._3 = _3;
    }

    public int get_4() {
        return _4;
    }

    public void set_4(int _4) {
        this._4 = _4;
    }

    public int get_5() {
        return _5;
    }

    public void set_5(int _5) {
        this._5 = _5;
    }

    public int get_6() {
        return _6;
    }

    public void set_6(int _6) {
        this._6 = _6;
    }

    public int get_7() {
        return _7;
    }

    public void set_7(int _7) {
        this._7 = _7;
    }

    public int get_8() {
        return _8;
    }

    public void set_8(int _8) {
        this._8 = _8;
    }

    public int get_9() {
        return _9;
    }

    public void set_9(int _9) {
        this._9 = _9;
    }

    public int get_10() {
        return _10;
    }

    public void set_10(int _10) {
        this._10 = _10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Count)) return false;

        Count count = (Count) o;

        if (_1 != count._1) return false;
        if (_2 != count._2) return false;
        if (_3 != count._3) return false;
        if (_4 != count._4) return false;
        if (_5 != count._5) return false;
        if (_6 != count._6) return false;
        if (_7 != count._7) return false;
        if (_8 != count._8) return false;
        if (_9 != count._9) return false;
        return _10 == count._10;

    }

    @Override
    public int hashCode() {
        int result = _1;
        result = 31 * result + _2;
        result = 31 * result + _3;
        result = 31 * result + _4;
        result = 31 * result + _5;
        result = 31 * result + _6;
        result = 31 * result + _7;
        result = 31 * result + _8;
        result = 31 * result + _9;
        result = 31 * result + _10;
        return result;
    }
}
