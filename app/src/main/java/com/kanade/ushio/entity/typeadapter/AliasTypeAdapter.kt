package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.Alias

class AliasTypeAdapter : TypeAdapter<Alias>() {
    override fun read(input: JsonReader): Alias {
        val alias = Alias()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return alias }
        // 这里有时会直接返回一个array
        when (input.peek()) {
            JsonToken.BEGIN_ARRAY -> readAliasArray(input, alias)
            JsonToken.BEGIN_OBJECT -> readAliasObj(input, alias)
            else -> {}
        }
        return alias
    }

    private fun readAliasObj(input: JsonReader, alias: Alias) {
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "0" -> alias._0 = input.nextString()
                "1" -> alias._1 = input.nextString()
                "2" -> alias._2 = input.nextString()
                "3" -> alias._3 = input.nextString()
                "4" -> alias._4 = input.nextString()
                "5" -> alias._5 = input.nextString()
                "zh" -> alias.zh = input.nextString()
                "en" -> alias.en = input.nextString()
                "jp" -> alias.jp = input.nextString()
                "kana" -> alias.kana = input.nextString()
                "romaji" -> alias.romaji = input.nextString()
                else -> input.skipValue()
            }
        }
        input.endObject()
    }

    private fun readAliasArray(input: JsonReader, alias: Alias) {
        val list = arrayListOf<String>()
        input.beginArray()
        while (input.hasNext()) {
            list.add(input.nextString())
        }
        input.endArray()
        alias.aliasString = list
    }

    override fun write(out: JsonWriter, value: Alias) {
        out.beginObject()
        out.name("0").value(value._0)
        out.name("1").value(value._1)
        out.name("2").value(value._2)
        out.name("3").value(value._3)
        out.name("4").value(value._4)
        out.name("5").value(value._5)
        out.name("zh").value(value.zh)
        out.name("en").value(value.en)
        out.name("jp").value(value.jp)
        out.name("kana").value(value.kana)
        out.name("romaji").value(value.romaji)
        out.endObject()
    }
}