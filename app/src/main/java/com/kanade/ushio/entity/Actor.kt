package com.kanade.ushio.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity
data class Actor(
        @PrimaryKey
        var id: Long = 0,
        var url: String? = null,
        var name: String? = null,
        @Embedded
        var images: Image? = null
) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readLong(),
                parcel.readString(),
                parcel.readString(),
                parcel.readParcelable(Image::class.java.classLoader)) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeLong(id)
                parcel.writeString(url)
                parcel.writeString(name)
                parcel.writeParcelable(images, flags)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Actor> {
                override fun createFromParcel(parcel: Parcel): Actor {
                        return Actor(parcel)
                }

                override fun newArray(size: Int): Array<Actor?> {
                        return arrayOfNulls(size)
                }
        }

}