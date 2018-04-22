package com.kanade.ushio.entity

import android.os.Parcel
import android.os.Parcelable

data class Image(
        var large: String? = null,
        var common: String? = null,
        var medium: String? = null,
        var small: String? = null,
        var grid: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(large)
        parcel.writeString(common)
        parcel.writeString(medium)
        parcel.writeString(small)
        parcel.writeString(grid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Image> {
        override fun createFromParcel(parcel: Parcel): Image {
            return Image(parcel)
        }

        override fun newArray(size: Int): Array<Image?> {
            return arrayOfNulls(size)
        }
    }

    fun getImageS2L(): String {
        return grid ?: small ?: medium ?: common ?: large ?: ""
    }

    fun getImageL2S(): String {
        return large ?: common ?: medium ?: small ?: grid ?: ""
    }

    fun getImageC2L(): String {
        return medium ?: common ?: large ?: small ?: grid ?: ""
    }

    fun getImageC2S(): String {
        return medium ?: small ?: grid ?: common ?: large ?: ""
    }
}