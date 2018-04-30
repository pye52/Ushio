package com.kanade.ushio.entity

data class User(
        var id: Long,
        var url: String,
        var username: String,
        var nickname: String,
        var avatar: Image,
        var sign: String
)