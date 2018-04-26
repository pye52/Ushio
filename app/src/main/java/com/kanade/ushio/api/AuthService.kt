package com.kanade.ushio.api

import com.kanade.ushio.entity.UserToken
import io.reactivex.Flowable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {
    @FormUrlEncoded
    @POST("/oauth/access_token")
    fun auth(@Field("grant_type") type: String, @Field("client_id") id: String,
                      @Field("client_secret") secret: String, @Field("code") code: String,
                      @Field("redirect_uri") uri: String): Flowable<UserToken>

    @FormUrlEncoded
    @POST("/oauth/access_token")
    fun refreshSync(@Field("grant_type") type: String, @Field("client_id") id: String,
                    @Field("client_secret") secret: String, @Field("refresh_token") refreshToken: String,
                    @Field("redirect_uri") uri: String): retrofit2.Call<UserToken>
}