package com.kanade.ushio.api;

import com.kanade.ushio.api.responses.GradeResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UpdateGradeService {
    @FormUrlEncoded
    @POST("/collection/{subjectId}/update?source=" + ApiManager.SOURCE)
    Observable<GradeResponse> updateCollection(
            @Path("subjectId") int subjectId,
            @Field("status") String status,
            @Field("rating") int rating,
            @Field("comment") String comment,
            @Query("auth") String auth);
}
