package com.example.easymealmvvm.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.easymealmvvm.data.model.Meal

@Dao
interface MealDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal: Meal)

    @Query("SELECT * FROM meal_table")
    suspend fun getMeal(): Meal

    @Delete
    suspend fun delete(meal: Meal)

    @Query("SELECT * FROM meal_table")
    fun getAllMeals(): LiveData<List<Meal>>

}