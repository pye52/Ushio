package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.subject.SubjectSimple

class SubjectSimpleTypeAdapter : TypeAdapter<SubjectSimple>() {
    private var ratingTypeAdapter = RatingTypeAdapter()
    private var imageTypeAdapter = ImageTypeAdapter()
    private var collectionTypeAdapter = CollectionTypeAdapter()

    override fun read(input: JsonReader): SubjectSimple {
        val subjectSimple = SubjectSimple()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return subjectSimple }
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "id" -> subjectSimple.id = input.nextInt()
                "url" -> subjectSimple.url = input.nextString()
                "type" -> subjectSimple.type = input.nextInt()
                "eps" -> subjectSimple.eps = input.nextInt()
                "name" -> subjectSimple.name = input.nextString()
                "name_cn" -> subjectSimple.nameCn = input.nextString()
                "summary" -> subjectSimple.summary = input.nextString()
                "air_date" -> subjectSimple.airDate = input.nextString()
                "air_weekday" -> subjectSimple.airWeekday = input.nextInt()
                "rating" -> subjectSimple.rating = ratingTypeAdapter.read(input)
                "rank" -> subjectSimple.rank = input.nextInt()
                "images" -> subjectSimple.images = imageTypeAdapter.read(input)
                "collection" -> subjectSimple.collection = collectionTypeAdapter.read(input)
                else -> input.skipValue()
            }
        }
        input.endObject()
        return subjectSimple
    }

    override fun write(out: JsonWriter, value: SubjectSimple) {
        out.beginObject()
        out.name("id").value(value.id)
        out.name("url").value(value.url)
        out.name("type").value(value.type)
        out.name("eps").value(value.eps)
        out.name("name").value(value.name)
        out.name("name_cn").value(value.nameCn)
        out.name("summary").value(value.summary)
        out.name("air_date").value(value.airDate)
        out.name("air_weekday").value(value.airWeekday)
        out.name("rating")
        ratingTypeAdapter.write(out, value.rating)
        out.name("rank").value(value.rank)
        out.name("images")
        imageTypeAdapter.write(out, value.images)
        out.name("collection")
        collectionTypeAdapter.write(out, value.collection)
        out.endObject()
    }
}