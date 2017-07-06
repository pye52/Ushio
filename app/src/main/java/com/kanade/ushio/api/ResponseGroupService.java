package com.kanade.ushio.api;

import com.kanade.ushio.entity.subject.SubjectEp;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 获取番组/专题信息，默认以最大信息量返回
 * Created by kanade on 2017/2/14.
 */

public interface ResponseGroupService {
    @GET("/subject/{subjectId}?responseGroup=large")
    Observable<SubjectEp> getSubjectLarge(@Path("subjectId") int subjectId);
}
