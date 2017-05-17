package com.kanade.ushio.entity.typeadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.kanade.ushio.entity.AniCollection
import com.kanade.ushio.entity.UserCollection
import io.realm.RealmList

class UserCollectionTypeAdapter : TypeAdapter<UserCollection>() {
    private val aniCollectionTypeAdapter = AniCollectionTypeAdapter()

    override fun read(input: JsonReader): UserCollection {
        val userCollection = UserCollection()
        if (input.peek() == JsonToken.NULL) { input.nextNull(); return userCollection }
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "id" -> userCollection.id = input.nextInt()
                "collections" -> {
                    val list = RealmList<AniCollection>()
                    input.beginArray()
                    while (input.hasNext()) {
                        list.add(aniCollectionTypeAdapter.read(input))
                    }
                    input.endArray()
                    userCollection.setCollections(list)
                }
                else -> input.skipValue()
            }
        }
        input.endObject()
        return userCollection
    }

    override fun write(out: JsonWriter, value: UserCollection) = Unit
}