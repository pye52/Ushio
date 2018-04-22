package com.kanade.ushio.entity

import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Entity
data class Crt(
        @PrimaryKey
        var id: Long = 0,
        var url: String? = null,
        var name: String? = null,
        @SerializedName("name_cn")
        @ColumnInfo(name = "crtNameCn")
        var nameCn: String? = null,
        @SerializedName("role_name")
        var roleName: String? = null,
        @Embedded
        var images: Image? = null,
        var comment: Int = 0,
        var collects: Int = 0,
        @Embedded
        var info: Info? = null,
        var updateTime: Long = 0,
        var actorIds: String = "",
        @Ignore
        var actors: List<Actor>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Image::class.java.classLoader),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readParcelable(Info::class.java.classLoader),
            parcel.readLong(),
            parcel.readString(),
            parcel.createTypedArrayList(Actor)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(url)
        parcel.writeString(name)
        parcel.writeString(nameCn)
        parcel.writeString(roleName)
        parcel.writeParcelable(images, flags)
        parcel.writeInt(comment)
        parcel.writeInt(collects)
        parcel.writeParcelable(info, flags)
        parcel.writeLong(updateTime)
        parcel.writeString(actorIds)
        parcel.writeTypedList(actors)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Crt> {
        override fun createFromParcel(parcel: Parcel): Crt {
            return Crt(parcel)
        }

        override fun newArray(size: Int): Array<Crt?> {
            return arrayOfNulls(size)
        }
    }
}