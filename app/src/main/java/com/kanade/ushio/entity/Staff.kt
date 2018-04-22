package com.kanade.ushio.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Entity
data class Staff(
        @PrimaryKey
        var id: Int = 0,
        var url: String? = null,
        var name: String? = null,
        @SerializedName("name_cn")
        var nameCn: String? = null,
        @SerializedName("role_name")
        var roleName: String? = null,
        @Embedded
        var images: Image? = null,
        var comment: Int = 0,
        var collects: Int = 0,
        var info: Info? = null,
        var jobs: List<String>? = null
) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readParcelable(Image::class.java.classLoader),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readParcelable(Info::class.java.classLoader),
                parcel.createStringArrayList()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeInt(id)
                parcel.writeString(url)
                parcel.writeString(name)
                parcel.writeString(nameCn)
                parcel.writeString(roleName)
                parcel.writeParcelable(images, flags)
                parcel.writeInt(comment)
                parcel.writeInt(collects)
                parcel.writeParcelable(info, flags)
                parcel.writeStringList(jobs)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Staff> {
                override fun createFromParcel(parcel: Parcel): Staff {
                        return Staff(parcel)
                }

                override fun newArray(size: Int): Array<Staff?> {
                        return arrayOfNulls(size)
                }
        }

}