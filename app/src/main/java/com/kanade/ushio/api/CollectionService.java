package com.kanade.ushio.api;

import com.kanade.ushio.entity.AniCollection;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

// 若用户并没有任何番剧收藏时，返回值为null
public interface CollectionService {
    @GET("/user/{userId}/collection?cat=watching")
    Observable<List<AniCollection>> get(@Path("userId") int userId);
}
