package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.subject.Actor

class ActorTypeAdapter : TypeAdapter<Actor>() {
    private val imageTypeAdapter = ImageTypeAdapter()

    override fun read(input: JsonReader): Actor {
        val actor = Actor()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return actor }
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "id" -> actor.id = input.nextInt()
                "url" -> actor.url = input.nextString()
                "name" -> actor.name = input.nextString()
                "images" -> actor.images = imageTypeAdapter.read(input)
                else -> input.skipValue()
            }
        }
        input.endObject()
        return actor
    }

    override fun write(out: JsonWriter, value: Actor) {
        out.beginObject()
        out.name("id").value(value.id)
        out.name("url").value(value.url)
        out.name("name").value(value.name)
        out.name("images")
        imageTypeAdapter.write(out, value.images)
        out.endObject()
    }
}