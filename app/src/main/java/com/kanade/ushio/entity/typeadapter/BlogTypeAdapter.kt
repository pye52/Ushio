package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.subject.Blog

class BlogTypeAdapter : TypeAdapter<Blog>() {
    private val userTypeAdapter = UserTypeAdapter()

    override fun read(input: JsonReader): Blog {
        val blog = Blog()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return blog }
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "id" -> blog.id = input.nextInt()
                "url" -> blog.url = input.nextString()
                "title" -> blog.title = input.nextString()
                "summary" -> blog.summary = input.nextString()
                "replies" -> blog.replies = input.nextInt()
                "timestamp" -> blog.timestamp = input.nextLong()
                "dateline" -> blog.dateline = input.nextString()
                "user" -> blog.user = userTypeAdapter.read(input)
                else -> input.skipValue()
            }
        }
        input.endObject()
        return blog
    }

    override fun write(out: JsonWriter, value: Blog) {
        out.beginObject()
        out.name("id").value(value.id)
        out.name("url").value(value.url)
        out.name("title").value(value.title)
        out.name("summary").value(value.summary)
        out.name("replies").value(value.replies)
        out.name("timestamp").value(value.timestamp)
        out.name("dateline").value(value.dateline)
        out.name("user")
        userTypeAdapter.write(out, value.user)
        out.endObject()
    }
}