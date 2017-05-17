package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.subject.Image

class ImageTypeAdapter : TypeAdapter<Image>() {
    override fun read(input: JsonReader): Image {
        val image = Image()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return image }
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "large" -> image.large = input.nextString()
                "common" -> image.common = input.nextString()
                "medium" -> image.medium = input.nextString()
                "small" -> image.small = input.nextString()
                "grid" -> image.grid = input.nextString()
                else -> input.skipValue()
            }
        }
        input.endObject()
        return image
    }

    override fun write(out: JsonWriter, value: Image) {
        out.beginObject()
        out.name("large").value(value.large)
        out.name("common").value(value.common)
        out.name("medium").value(value.medium)
        out.name("small").value(value.small)
        out.name("grid").value(value.grid)
        out.endObject()
    }
}