package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.WatchStatus

class WatchStatusTypeAdapter : TypeAdapter<WatchStatus>() {
    override fun read(input: JsonReader): WatchStatus {
        val watchStatus = WatchStatus()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return watchStatus }
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "id" -> watchStatus.id = input.nextInt()
                "css_name" -> watchStatus.cssName = input.nextString()
                "url_name" -> watchStatus.urlName = input.nextString()
                "cn_name" -> watchStatus.cnName = input.nextString()
                else -> input.skipValue()
            }
        }
        input.endObject()
        return watchStatus
    }

    override fun write(out: JsonWriter, value: WatchStatus) {
        out.beginObject()
        out.name("id").value(value.id)
        out.name("css_name").value(value.cssName)
        out.name("url_name").value(value.urlName)
        out.name("cn_name").value(value.cnName)
        out.endObject()
    }
}