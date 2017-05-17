package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.subject.Info
import com.kanade.ushio.entity.subject.RealmString
import io.realm.RealmList

class InfoTypeAdapter : TypeAdapter<Info>() {
    private var aliasTypeAdapter = AliasTypeAdapter()

    override fun read(input: JsonReader): Info {
        val info = Info()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return info }
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "name_cn" -> info.nameCn = input.nextString()
                "alias" -> info.alias = aliasTypeAdapter.read(input)
                "gender" -> info.gender = input.nextString()
                "birth" -> info.birth = input.nextString()
                "height" -> info.height = input.nextString()
                "weight" -> info.weight = input.nextString()
                "bwh" -> info.bwh = input.nextString()
                "bloodtype" -> info.bloodtype = input.nextString()
                "source" -> {
                    if (input.peek() == JsonToken.STRING) {
                        readSourceString(input, info)
                    } else {
                        readSourceArray(input, info)
                    }

                }
                else -> input.skipValue()
            }
        }
        input.endObject()
        return info
    }

    override fun write(out: JsonWriter, value: Info) {
        out.beginObject()
        out.name("name_cn").value(value.nameCn)
        out.name("alias")
        aliasTypeAdapter.write(out, value.alias)
        out.name("gender").value(value.gender)
        out.name("birth").value(value.birth)
        out.name("height").value(value.height)
        out.name("weight").value(value.weight)
        out.name("bwh").value(value.bwh)
        out.name("bloodtype").value(value.bloodtype)
        out.name("source")
        out.beginArray()
        value.source.forEach { out.value(it.realmString) }
        out.endArray()
        out.endObject()
    }

    private fun readSourceArray(input: JsonReader, info: Info) {
        input.beginArray()
        val list = RealmList<RealmString>()
        while (input.hasNext()) {
            list.add(RealmString(input.nextString()))
        }
        input.endArray()
        info.source = list
    }

    private fun readSourceString(input: JsonReader, info: Info) {
        val list = RealmList<RealmString>()
        list.add(RealmString(input.nextString()))
        info.source = list
    }
}