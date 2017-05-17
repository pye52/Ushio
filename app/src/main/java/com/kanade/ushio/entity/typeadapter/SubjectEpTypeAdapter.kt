package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.Ep
import com.kanade.ushio.entity.subject.*
import io.realm.RealmList

class SubjectEpTypeAdapter : TypeAdapter<SubjectEp>() {
    private val ratingTypeAdapter = RatingTypeAdapter()
    private val imageTypeAdapter = ImageTypeAdapter()
    private val collectionTypeAdapter = CollectionTypeAdapter()
    private val epTypeAdapter = EpTypeAdapter()
    private val crtTypeAdapter = CrtTypeAdapter()
    private val staffTypeAdapter = StaffTypeAdapter()
    private val topicTypeAdapter = TopicTypeAdapter()
    private val blogTypeAdapter = BlogTypeAdapter()

    override fun read(input: JsonReader): SubjectEp {
        val subjectEp = SubjectEp()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return subjectEp }
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "id" -> subjectEp.id = input.nextInt()
                "url" -> subjectEp.url = input.nextString()
                "type" -> subjectEp.type = input.nextInt()
                "name" -> subjectEp.name = input.nextString()
                "name_cn" -> subjectEp.nameCn = input.nextString()
                "summary" -> subjectEp.summary = input.nextString()
                "air_date" -> subjectEp.airDate = input.nextString()
                "air_weekday" -> subjectEp.airWeekday = input.nextInt()
                "rating" -> subjectEp.rating = ratingTypeAdapter.read(input)
                "rank" -> subjectEp.rank = input.nextInt()
                "images" -> subjectEp.images = imageTypeAdapter.read(input)
                "collection" -> subjectEp.collection = collectionTypeAdapter.read(input)
                "eps" -> readEpList(input, subjectEp)
                "crt" -> readCrtList(input, subjectEp)
                "staff" -> readStaffList(input, subjectEp)
                "topic" -> readTopicList(input, subjectEp)
                "blog" -> readBlogList(input, subjectEp)
                else -> input.skipValue()
            }
        }
        input.endObject()
        return subjectEp
    }

    override fun write(out: JsonWriter, value: SubjectEp) = Unit

    private fun readEpList(input: JsonReader, subjectEp: SubjectEp) {
        val list = RealmList<Ep>()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); subjectEp.eps = list; return }
        input.beginArray()
        while (input.hasNext()) {
            list.add(epTypeAdapter.read(input))
        }
        input.endArray()
        subjectEp.eps = list
    }

    private fun readCrtList(input: JsonReader, subjectEp: SubjectEp) {
        val list = RealmList<Crt>()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); subjectEp.crt = list; return }
        input.beginArray()
        while (input.hasNext()) {
            list.add(crtTypeAdapter.read(input))
        }
        input.endArray()
        subjectEp.crt = list
    }

    private fun readStaffList(input: JsonReader, subjectEp: SubjectEp) {
        val list = RealmList<Staff>()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); subjectEp.staff = list; return }
        input.beginArray()
        while (input.hasNext()) {
            list.add(staffTypeAdapter.read(input))
        }
        input.endArray()
        subjectEp.staff = list
    }

    private fun readTopicList(input: JsonReader, subjectEp: SubjectEp) {
        val list = RealmList<Topic>()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); subjectEp.topic = list; return }
        input.beginArray()
        while (input.hasNext()) {
            list.add(topicTypeAdapter.read(input))
        }
        input.endArray()
        subjectEp.topic = list
    }

    private fun readBlogList(input: JsonReader, subjectEp: SubjectEp) {
        val list = RealmList<Blog>()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); subjectEp.blog = list; return }
        input.beginArray()
        while (input.hasNext()) {
            list.add(blogTypeAdapter.read(input))
        }
        input.endArray()
        subjectEp.blog = list
    }
}