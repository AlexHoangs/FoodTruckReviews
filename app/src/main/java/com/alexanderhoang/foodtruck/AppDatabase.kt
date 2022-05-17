package com.alexanderhoang.foodtruck

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.alexanderhoang.LocalDateTimeTypeConverters
import java.time.LocalDateTime

@Database(entities = [FoodTruck::class, FoodItem::class], version=1)
@TypeConverters(LocalDateTimeTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodTruckDao(): FoodTruckDao
    abstract fun foodItemDao(): FoodItemDao
}