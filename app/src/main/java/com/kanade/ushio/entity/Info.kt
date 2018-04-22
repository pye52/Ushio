package com.kanade.ushio.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.TypeConverters
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.kanade.ushio.entity.typeadapter.InfoTypeAdapter
import com.kanade.ushio.entity.typeconverter.StringListTypeConverter

@TypeConverters(StringListTypeConverter::class)
@JsonAdapter(InfoTypeAdapter::class)
data class Info(
        @SerializedName("name_cn")
        var nameCn: String? = null,
        @Embedded
        var alias: Alias? = null,
        var gender: String? = null,
        var birth: String? = null,
        var height: String? = null,
        var weight: String? = null,
        var bwh: String? = null,
        var bloodtype: String? = null,
        var source: List<String>? = null
) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readParcelable(Alias::class.java.classLoader),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.createStringArrayList()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(nameCn)
                parcel.writeParcelable(alias, flags)
                parcel.writeString(gender)
                parcel.writeString(birth)
                parcel.writeString(height)
                parcel.writeString(weight)
                parcel.writeString(bwh)
                parcel.writeString(bloodtype)
                parcel.writeStringList(source)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Info> {
                override fun createFromParcel(parcel: Parcel): Info {
                        return Info(parcel)
                }

                override fun newArray(size: Int): Array<Info?> {
                        return arrayOfNulls(size)
                }
        }
}