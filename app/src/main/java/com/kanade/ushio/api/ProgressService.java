package com.kanade.ushio.api;

import com.kanade.ushio.api.responses.ProgressResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

// 当用户没有对任何剧集有做操作时，则返回为null
public interface ProgressService {
    @GET("/user/{userId}/progress?source=" + ApiManager.SOURCE)
    Observable<ProgressResponse> getSubjectProgress(@Path("userId") int userId, @Query("auth") String auth, @Query("subject_id") int subjectId);
}
