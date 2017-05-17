package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.Weekday

class WeekdayTypeAdapter : TypeAdapter<Weekday>() {
    override fun read(input: JsonReader): Weekday {
        val weekday = Weekday()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return weekday }
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "en" -> weekday.en = input.nextString()
                "cn" -> weekday.cn = input.nextString()
                "ja" -> weekday.ja = input.nextString()
                "id" -> weekday.id = input.nextString()
                else -> input.skipValue()
            }
        }
        input.endObject()
        return weekday
    }

    override fun write(out: JsonWriter, value: Weekday) = Unit
}