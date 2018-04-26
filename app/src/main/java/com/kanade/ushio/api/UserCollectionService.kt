package com.kanade.ushio.api

import com.kanade.ushio.entity.UserCollection
import io.reactivex.Flowable
import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserCollectionService {
    @GET("/user/{username}/collection")
    fun getUserCollection(@Path("username") username: String, @Query("cat") cat: String = "watching",
                          @Query("responseGroup") responseGroup: String = "medium"): Flowable<List<UserCollection>>
}