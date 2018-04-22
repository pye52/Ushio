package com.kanade.ushio.entity

import android.arch.persistence.room.TypeConverters
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.JsonAdapter
import com.kanade.ushio.entity.typeadapter.AliasTypeAdapter
import com.kanade.ushio.entity.typeconverter.StringListTypeConverter

@TypeConverters(StringListTypeConverter::class)
@JsonAdapter(AliasTypeAdapter::class)
data class Alias(
        var aliasString: List<String>? = null,
        var _0: String? = "",
        var _1: String? = "",
        var _2: String? = "",
        var _3: String? = "",
        var _4: String? = "",
        var _5: String? = "",
        var zh: String? = "",
        var en: String? = "",
        var jp: String? = "",
        var kana: String? = "",
        var romaji: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.createStringArrayList(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(aliasString)
        parcel.writeString(_0)
        parcel.writeString(_1)
        parcel.writeString(_2)
        parcel.writeString(_3)
        parcel.writeString(_4)
        parcel.writeString(_5)
        parcel.writeString(zh)
        parcel.writeString(en)
        parcel.writeString(jp)
        parcel.writeString(kana)
        parcel.writeString(romaji)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Alias> {
        override fun createFromParcel(parcel: Parcel): Alias {
            return Alias(parcel)
        }

        override fun newArray(size: Int): Array<Alias?> {
            return arrayOfNulls(size)
        }
    }
}