package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.subject.Topic

class TopicTypeAdapter : TypeAdapter<Topic>() {
    private val userTypeAdapter = UserTypeAdapter()

    override fun read(input: JsonReader): Topic {
        val topic = Topic()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return topic }
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "id" -> topic.id = input.nextInt()
                "url" -> topic.url = input.nextString()
                "title" -> topic.title = input.nextString()
                "main_id" -> topic.mainId = input.nextInt()
                "timestamp" -> topic.timestamp = input.nextLong()
                "lastpost" -> topic.lastpost = input.nextLong()
                "replies" -> topic.replies = input.nextInt()
                "user" -> topic.user = userTypeAdapter.read(input)
                else -> input.skipValue()
            }
        }
        input.endObject()
        return topic
    }

    override fun write(out: JsonWriter, value: Topic) {
        out.beginObject()
        out.name("id").value(value.id)
        out.name("url").value(value.url)
        out.name("title").value(value.title)
        out.name("main_id").value(value.mainId)
        out.name("timestamp").value(value.timestamp)
        out.name("lastpost").value(value.lastpost)
        out.name("replies").value(value.replies)
        out.name("user")
        userTypeAdapter.write(out, value.user)
        out.endObject()
    }
}