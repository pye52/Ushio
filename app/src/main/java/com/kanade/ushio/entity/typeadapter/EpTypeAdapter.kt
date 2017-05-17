package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.Ep

class EpTypeAdapter : TypeAdapter<Ep>() {
    private val watchStatusTypeAdapter = WatchStatusTypeAdapter()

    override fun read(input: JsonReader): Ep {
        val ep = Ep()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return ep }
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "id" -> ep.id = input.nextInt()
                "url" -> ep.url = input.nextString()
                "type" -> ep.type = input.nextInt()
                "sort" -> ep.sort = input.nextInt()
                "name" -> ep.name = input.nextString()
                "name_cn" -> ep.nameCn = input.nextString()
                "duration" -> ep.duration = input.nextString()
                "airdate" -> ep.airdate = input.nextString()
                "comment" -> ep.comment = input.nextInt()
                "desc" -> ep.desc = input.nextString()
                "status" -> ep.status = input.nextString()
                "watchStatus" -> ep.watchStatus = watchStatusTypeAdapter.read(input)
                else -> input.skipValue()
            }
        }
        input.endObject()
        return ep
    }

    override fun write(out: JsonWriter, value: Ep) {
        out.beginObject()
        out.name("id").value(value.id)
        out.name("url").value(value.url)
        out.name("type").value(value.type)
        out.name("sort").value(value.sort)
        out.name("name").value(value.name)
        out.name("name_cn").value(value.nameCn)
        out.name("duration").value(value.duration)
        out.name("airdate").value(value.airdate)
        out.name("comment").value(value.comment)
        out.name("desc").value(value.desc)
        out.name("status").value(value.status)
        out.name("watchStatus")
        watchStatusTypeAdapter.write(out, value.watchStatus)
        out.endObject()
    }
}