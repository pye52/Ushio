package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.subject.Actor
import com.kanade.ushio.entity.subject.Crt
import io.realm.RealmList

class CrtTypeAdapter : TypeAdapter<Crt>() {
    private val imageTypeAdapter = ImageTypeAdapter()
    private val infoTypeAdapter = InfoTypeAdapter()
    private val actorTypeAdapter = ActorTypeAdapter()

    override fun read(input: JsonReader): Crt {
        val crt = Crt()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return crt }
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "id" -> crt.id = input.nextInt()
                "url" -> crt.url = input.nextString()
                "name" -> crt.name = input.nextString()
                "name_cn" -> crt.nameCn = input.nextString()
                "role_name" -> crt.roleName = input.nextString()
                "images" -> crt.images = imageTypeAdapter.read(input)
                "comment" -> crt.comment = input.nextInt()
                "collects" -> crt.collects = input.nextInt()
                "info" -> crt.info = infoTypeAdapter.read(input)
                "actors" -> readActorsList(input, crt)
                else -> input.skipValue()
            }
        }
        input.endObject()
        return crt
    }

    override fun write(out: JsonWriter, value: Crt) {
        out.beginObject()
        out.name("id").value(value.id)
        out.name("url").value(value.url)
        out.name("name").value(value.name)
        out.name("name_cn").value(value.nameCn)
        out.name("role_name").value(value.roleName)
        out.name("images")
        imageTypeAdapter.write(out, value.images)
        out.name("comment").value(value.comment)
        out.name("collects").value(value.collects)
        out.name("info")
        infoTypeAdapter.write(out, value.info)
        out.name("actors")
        out.beginArray()
        value.actors.forEach { actorTypeAdapter.write(out, it) }
        out.endArray()
        out.endObject()
    }

    private fun readActorsList(input: JsonReader, crt: Crt) {
        val list = RealmList<Actor>()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); crt.actors = list; return }
        input.beginArray()
        while (input.hasNext()) {
            list.add(actorTypeAdapter.read(input))
        }
        input.endArray()
        crt.actors = list
    }
}