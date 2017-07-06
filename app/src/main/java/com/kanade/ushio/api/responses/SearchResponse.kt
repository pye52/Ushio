package com.kanade.ushio.api.responses

import com.google.gson.annotations.JsonAdapter
import com.kanade.ushio.entity.subject.SubjectSimple
import com.kanade.ushio.entity.typeadapter.SearchResponseTypeAdapter
import java.util.*

@JsonAdapter(SearchResponseTypeAdapter::class)
data class SearchResponse(
        var results: Int,
        var list: List<SubjectSimple>
) {
    companion object {
        @JvmStatic
        fun create(): SearchResponse = SearchResponse(0, Collections.emptyList())
    }
}
