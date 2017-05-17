package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.api.responses.SearchResponse
import com.kanade.ushio.entity.subject.SubjectSimple

class SearchResponseTypeAdapter : TypeAdapter<SearchResponse>() {
    private val subjectSimpleTypeAdapter = SubjectSimpleTypeAdapter()

    override fun read(input: JsonReader): SearchResponse {
        val response = SearchResponse.create()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return response }
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "results" -> response.results = input.nextInt()
                "list" -> {
                    val list = ArrayList<SubjectSimple>()
                    input.beginArray()
                    while (input.hasNext()) {
                        list.add(subjectSimpleTypeAdapter.read(input))
                    }
                    input.endArray()
                    response.list = list
                }
            }
        }
        input.endObject()
        return response
    }

    override fun write(out: JsonWriter, value: SearchResponse) = Unit
}