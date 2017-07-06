package com.kanade.ushio.api;

import com.kanade.ushio.api.ApiManager;
import com.kanade.ushio.api.responses.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UpdateProgressService {
    @FormUrlEncoded
    @POST("/ep/{epId}/status/{status}?source=" + ApiManager.SOURCE)
    Observable<BaseResponse> updateEp(@Field("auth") String auth, @Path("epId") int epId, @Path("status") String status);
}
