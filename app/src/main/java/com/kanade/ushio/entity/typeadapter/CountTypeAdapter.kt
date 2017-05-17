package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.subject.Count

class CountTypeAdapter : TypeAdapter<Count>() {
    override fun read(input: JsonReader): Count {
        val count = Count()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return count }
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "1" -> count._1 = input.nextInt()
                "2" -> count._2 = input.nextInt()
                "3" -> count._3 = input.nextInt()
                "4" -> count._4 = input.nextInt()
                "5" -> count._5 = input.nextInt()
                "6" -> count._6 = input.nextInt()
                "7" -> count._7 = input.nextInt()
                "8" -> count._8 = input.nextInt()
                "9" -> count._9 = input.nextInt()
                "10" -> count._10 = input.nextInt()
                else -> input.skipValue()
            }
        }
        input.endObject()
        return count
    }

    override fun write(out: JsonWriter, value: Count) {
        out.beginObject()
        out.name("1").value(value._1)
        out.name("2").value(value._2)
        out.name("3").value(value._3)
        out.name("4").value(value._4)
        out.name("5").value(value._5)
        out.name("6").value(value._6)
        out.name("7").value(value._7)
        out.name("8").value(value._8)
        out.name("9").value(value._9)
        out.name("10").value(value._10)
        out.endObject()
    }
}