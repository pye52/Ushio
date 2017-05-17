package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.AniCollection

class AniCollectionTypeAdapter : TypeAdapter<AniCollection>() {
    private val subjectSimpleTypeAdapter = SubjectSimpleTypeAdapter()

    override fun read(input: JsonReader): AniCollection {
        val aniCollection = AniCollection()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return aniCollection }
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "name" -> aniCollection.name = input.nextString()
                "ep_status" -> aniCollection.epStatus = input.nextInt()
                "lasttouch" -> aniCollection.lasttouch = input.nextLong()
                "subject" -> aniCollection.subjectSimple = subjectSimpleTypeAdapter.read(input)
                else -> input.skipValue()
            }
        }
        input.endObject()
        return aniCollection
    }

    override fun write(out: JsonWriter, value: AniCollection) = Unit
}