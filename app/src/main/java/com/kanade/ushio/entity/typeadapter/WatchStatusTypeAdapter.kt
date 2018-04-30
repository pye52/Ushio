package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.Status
import com.kanade.ushio.entity.WatchStatus

class WatchStatusTypeAdapter : TypeAdapter<WatchStatus>() {
    override fun read(input: JsonReader): WatchStatus {
        val watchStatus = WatchStatus()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return watchStatus }
        watchStatus.epId = input.nextLong()
        input.beginObject()
        val status = Status()
        while (input.hasNext()) {
            when (input.nextName()) {
                "id" -> status.id = input.nextInt()
                "css_name" -> status.cssName = input.nextString()
                "url_name" -> status.urlName = input.nextString()
                "cn_name" -> status.cnName = input.nextString()
                else -> input.skipValue()
            }
        }
        input.endObject()
        watchStatus.status = status
        return watchStatus
    }

    override fun write(out: JsonWriter, value: WatchStatus) {
        out.beginObject()
        out.name("epId").value(value.epId)
        out.name("id").value(value.status?.id)
        out.name("css_name").value(value.status?.cssName)
        out.name("url_name").value(value.status?.urlName)
        out.name("cn_name").value(value.status?.cnName)
        out.endObject()
    }
}