package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.Calendar
import com.kanade.ushio.entity.subject.SubjectSimple
import io.realm.RealmList

class CalendarTypeAdapter : TypeAdapter<Calendar>() {
    private val weekdayTypeAdapter = WeekdayTypeAdapter()
    private val subjectSimpleTypeAdapter = SubjectSimpleTypeAdapter()

    override fun read(input: JsonReader): Calendar{
        val calendar = Calendar()
        if (input.peek() == JsonToken.NULL) {
            input.nextNull(); return calendar
        }
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "weekday" -> calendar.weekday = weekdayTypeAdapter.read(input)
                "items" -> {
                    val list = RealmList<SubjectSimple>()
                    input.beginArray()
                    while (input.hasNext()) {
                        list.add(subjectSimpleTypeAdapter.read(input))
                    }
                    input.endArray()
                    calendar.items = list
                }
                else -> input.skipValue()
            }
        }
        input.endObject()
        return calendar
    }

    override fun write(out: JsonWriter, value: Calendar) = Unit
}