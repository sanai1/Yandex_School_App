package com.example.database

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class Converters {
    @TypeConverter
    fun fromTimestep(value: Long): LocalDateTime? =
        Instant.ofEpochMilli(value).atZone(ZoneOffset.UTC).toLocalDateTime()

    @TypeConverter
    fun dateToTimestep(date: LocalDateTime): Long =
        date.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()
}