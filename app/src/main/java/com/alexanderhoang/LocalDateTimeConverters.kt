package com.alexanderhoang

import androidx.room.TypeConverter
import java.time.LocalDateTime

class LocalDateTimeTypeConverters() {
    @TypeConverter
    fun fromString(s: String): LocalDateTime = LocalDateTime.parse(s)
    @TypeConverter
    fun toString(ldt: LocalDateTime): String = ldt.toString();
}