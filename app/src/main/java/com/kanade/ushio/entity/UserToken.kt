package com.kanade.ushio.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class UserToken(
        @PrimaryKey
        @SerializedName("user_id")
        var userId: Long,
        @SerializedName("access_token")
        var token: String,
        @SerializedName("expires_in")
        var expires: Int,
        @SerializedName("token_type")
        var tokenType: String,
        var score: Int,
        @SerializedName("refresh_token")
        var refreshToken: String
)