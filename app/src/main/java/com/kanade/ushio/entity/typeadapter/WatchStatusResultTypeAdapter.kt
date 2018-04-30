package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.WatchStatus
import com.kanade.ushio.entity.WatchStatusResult

class WatchStatusResultTypeAdapter : TypeAdapter<WatchStatusResult>() {
    private val typeAdapter = WatchStatusTypeAdapter()

    override fun write(out: JsonWriter?, value: WatchStatusResult) = Unit

    override fun read(input: JsonReader): WatchStatusResult {
        val list = arrayListOf<WatchStatus>()
        val result = WatchStatusResult(0, list)
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return result }
        result.subjectId = input.nextLong()
        input.beginArray()
        while (input.hasNext()) {
            list.add(typeAdapter.read(input))
        }
        input.endArray()
        return result
    }
}