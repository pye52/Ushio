package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.User

class UserTypeAdapter : TypeAdapter<User>() {
    private val imageTypeAdapter = ImageTypeAdapter()

    override fun read(input: JsonReader): User {
        val user = User()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return user }
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "id" -> user.id = input.nextInt()
                "url" -> user.url = input.nextString()
                "username" -> user.username = input.nextString()
                "nickname" -> user.nickname = input.nextString()
                "avatar" -> user.avatar = imageTypeAdapter.read(input)
                "sigh" -> if (input.peek() != JsonToken.NULL) user.sign = input.nextString()
                "auth" -> user.auth = input.nextString()
                "auth_encode" -> user.authEncode = input.nextString()
                else -> input.skipValue()
            }
        }
        input.endObject()
        return user
    }

    override fun write(out: JsonWriter, value: User) {
        out.beginObject()
        out.name("id").value(value.id)
        out.name("url").value(value.url)
        out.name("username").value(value.username)
        out.name("nickname").value(value.nickname)
        out.name("avatar")
        imageTypeAdapter.write(out, value.avatar)
        out.name("sigh").value(value.sign)
        out.name("auth").value(value.auth)
        out.name("auth_encode").value(value.authEncode)
        out.endObject()
    }
}