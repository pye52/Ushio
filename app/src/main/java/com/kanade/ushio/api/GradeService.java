package com.kanade.ushio.api;

import com.kanade.ushio.api.responses.GradeResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

// 当用户对该番剧没有评价过时，返回为null
public interface GradeService {
    @GET("/collection/{subjectId}?source=" + ApiManager.SOURCE)
    Observable<GradeResponse> getSubjectCollection(@Path("subjectId") int subjectId, @Query("auth") String auth);
}
