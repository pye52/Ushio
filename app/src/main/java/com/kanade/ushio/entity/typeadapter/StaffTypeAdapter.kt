package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.subject.RealmString
import com.kanade.ushio.entity.subject.Staff
import io.realm.RealmList

class StaffTypeAdapter : TypeAdapter<Staff>() {
    private var imageTypeAdapter = ImageTypeAdapter()
    private var infoTypeAdapter = InfoTypeAdapter()

    override fun read(input: JsonReader): Staff {
        val staff = Staff()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return staff }
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "id" -> staff.id = input.nextInt()
                "url" -> staff.url = input.nextString()
                "name" -> staff.name = input.nextString()
                "name_cn" -> staff.nameCn = input.nextString()
                "role_name" -> staff.roleName = input.nextString()
                "images" -> staff.images = imageTypeAdapter.read(input)
                "comment" -> staff.comment = input.nextInt()
                "collects" -> staff.collects = input.nextInt()
                "info" -> staff.info = infoTypeAdapter.read(input)
                "jobs" -> {
                    input.beginArray()
                    val list = RealmList<RealmString>()
                    while (input.hasNext()) {
                        val realmStr = RealmString()
                        realmStr.realmString = input.nextString()
                        list.add(realmStr)

                    staff.jobs = list}
                    input.endArray()
                }
                else -> input.skipValue()
            }
        }
        input.endObject()
        return staff
    }

    override fun write(out: JsonWriter, value: Staff) {
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
        out.name("jobs")
        out.beginArray()
        value.jobs.forEach { out.value(it.realmString) }
        out.endArray()
        out.endObject()
    }
}