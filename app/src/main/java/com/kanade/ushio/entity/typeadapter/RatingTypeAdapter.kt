package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.subject.Rating

class RatingTypeAdapter : TypeAdapter<Rating>() {
    private var countTypeAdapter = CountTypeAdapter()

    override fun read(input: JsonReader): Rating {
        val rating = Rating()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return rating }
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "total" -> rating.total = input.nextInt()
                "count" -> rating.count = countTypeAdapter.read(input)
                "score" -> rating.score = input.nextDouble()
                else -> input.skipValue()
            }
        }
        input.endObject()
        return rating
    }

    override fun write(out: JsonWriter, value: Rating) {
        out.beginObject()
        out.name("total").value(value.total)
        out.name("count")
        countTypeAdapter.write(out, value.count)
        out.name("score").value(value.score)
        out.endObject()
    }
}