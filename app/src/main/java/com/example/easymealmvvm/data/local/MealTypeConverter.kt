package com.example.easymealmvvm.data.local

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverter {

    @TypeConverter
    fun fromAnyToString(value: Any?): String {
        return value?.toString() ?: ""
    }

    @TypeConverter
    fun fromStringToAny(value: String?): Any? {
        return if (value.isNullOrEmpty()) null else value
    }


}