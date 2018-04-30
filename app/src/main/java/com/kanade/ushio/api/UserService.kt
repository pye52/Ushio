package com.kanade.ushio.api

import com.kanade.ushio.entity.User
import com.kanade.ushio.entity.UserCollection
import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("/user/{username}")
    fun queryUser(@Path("username") username: String):Flowable<User>

    @GET("/user/{username}/collection")
    fun queryUserCollection(@Path("username") username: String, @Query("cat") cat: String = "watching",
                            @Query("responseGroup") responseGroup: String = "medium"): Flowable<List<UserCollection>>

    @GET("/user/{username}/collection")
    fun queryUserCollectionSync(@Path("username") username: String, @Query("cat") cat: String = "watching",
                            @Query("responseGroup") responseGroup: String = "medium"): Call<List<UserCollection>>
}