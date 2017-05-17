package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.subject.Collection

class CollectionTypeAdapter : TypeAdapter<Collection>() {
    override fun read(input: JsonReader): Collection {
        val collection = Collection()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return collection }
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "wish" -> collection.wish = input.nextInt()
                "collect" -> collection.collect = input.nextInt()
                "doing" -> collection.doing = input.nextInt()
                "on_hold" -> collection.onHold = input.nextInt()
                "dropped" -> collection.dropped = input.nextInt()
                else -> input.skipValue()
            }
        }
        input.endObject()
        return collection
    }

    override fun write(out: JsonWriter, value: Collection) {
        out.beginObject()
        out.name("wish").value(value.wish)
        out.name("collect").value(value.collect)
        out.name("doing").value(value.doing)
        out.name("on_hold").value(value.onHold)
        out.name("dropped").value(value.dropped)
        out.endObject()
    }
}