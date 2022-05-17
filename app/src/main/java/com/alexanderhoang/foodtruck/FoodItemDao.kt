package com.alexanderhoang.foodtruck

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FoodItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFoodItems(foodItems: List<FoodItem>)

    @Query("SELECT * FROM foodItem")
    fun getAll(): List<FoodItem>

    @Query("SELECT * FROM foodItem WHERE truckId = :truckId")
    fun getAllTruckItems(truckId: String): List<FoodItem>
}