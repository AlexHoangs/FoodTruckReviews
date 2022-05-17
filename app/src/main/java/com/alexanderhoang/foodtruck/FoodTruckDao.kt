package com.alexanderhoang.foodtruck

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FoodTruckDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFoodTrucks(foodTrucks: List<FoodTruck>)

    @Query("SELECT * FROM foodTruck")
    fun getAll(): List<FoodTruck>
}