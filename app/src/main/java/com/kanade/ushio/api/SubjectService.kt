package com.kanade.ushio.api

import com.kanade.ushio.entity.LargeSubject
import com.kanade.ushio.entity.SubjectCollection
import io.reactivex.Flowable
import retrofit2.http.*

interface SubjectService {
    @GET("/subject/{subjectId}")
    fun querySubject(@Path("subjectId") subjectId: Long, @Query("responseGroup") responseGroup: String = "large"): Flowable<LargeSubject>

    @GET("/subject/{subjectId}/ep")
    fun querySubjectEps(@Path("subjectId") subjectId: Long): Flowable<LargeSubject>

    @POST("/collection/{subjectId}/update")
    @FormUrlEncoded
    fun updateSubject(@Path("subjectId") subjectId: Long, @Field("status") status: String, @Field("comment") comment: String,
                      @Field("tags") tags: String, @Field("rating") rating: Int, @Field("privacy") privacy: Int): Flowable<SubjectCollection>

    @GET("/collection/{subjectId}")
    fun querySubjectCollection(@Path("subjectId") subjectId: Long): Flowable<SubjectCollection>
}