package com.kanade.ushio.api

import com.kanade.ushio.entity.ApiResult
import com.kanade.ushio.entity.WatchStatusResult
import io.reactivex.Flowable
import retrofit2.http.*

interface SubjectProgressService {
    @GET("/user/{username}/progress")
    fun getProgress(@Path("username") username: String, @Query("subject_id") id: Long): Flowable<WatchStatusResult>

    @POST("/ep/{epId}/status/{status}")
    fun updateProgress(@Path("epId") epId: Long, @Path("status") statusStr: String): Flowable<ApiResult>

    @POST("/subject/{subjectId}/update/watched_eps")
    @FormUrlEncoded
    fun updateAnimationProgress(@Path("subjectId") subjectId: Long, @Field("watched_eps") epsId: Long): Flowable<ApiResult>

    @POST("/subject/{subjectId}/update/watched_eps")
    @FormUrlEncoded
    fun updateBookProgress(@Path("subjectId") subjectId: Long, @Field("watched_vols") volsId: Long): Flowable<ApiResult>

}