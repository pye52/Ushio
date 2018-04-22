package com.kanade.ushio.entity

data class ApiResult(
        var request: String,
        var code: Int,
        var error: String
)