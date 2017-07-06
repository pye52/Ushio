package com.kanade.ushio.api;

import com.kanade.ushio.entity.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthService {
    @FormUrlEncoded
    @POST("/auth?source=" + ApiManager.SOURCE)
    Observable<User> auth(@Field("username") String username, @Field("password") String password);
}
