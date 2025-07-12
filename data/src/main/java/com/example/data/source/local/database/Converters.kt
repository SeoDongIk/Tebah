package com.example.data.source.local.database

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    private val json = Json

    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return json.encodeToString(value ?: emptyList())
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return json.decodeFromString(value)
    }
}