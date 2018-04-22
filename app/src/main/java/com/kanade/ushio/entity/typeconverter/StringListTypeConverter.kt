package com.kanade.ushio.entity.typeconverter

import android.arch.persistence.room.TypeConverter
import android.text.TextUtils

class StringListTypeConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromStringList(list: List<String>?): String {
            return list?.joinToString("|") ?: ""
        }

        @TypeConverter
        @JvmStatic
        fun string2StringList(str: String): List<String> {
            val list = arrayListOf<String>()
            if (TextUtils.isEmpty(str)) {
                return list
            } else {
                return str.split("|")
            }
        }
    }
}