package com.kanade.ushio.api;

import com.kanade.ushio.api.responses.SearchResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SearchService {
    /**
     * @param results 搜索结果条数(建议为10条)
     * @param start 搜索开始位置(用于翻页)
     */
    @GET("/search/subject/{query}?responseGroup=large")
    Observable<SearchResponse> search(@Path("query") String query,
                                      @Query("max_results") int results,
                                      @Query("start") int start);
}
