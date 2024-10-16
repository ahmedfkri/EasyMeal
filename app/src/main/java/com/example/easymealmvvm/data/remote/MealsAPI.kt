package com.example.easymealmvvm.data.remote

import com.example.easymealmvvm.data.Meals
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsAPI {


    @GET("random.php")
    fun getRandomMeal(): Call<Meals>

    @GET("lookup.php?")
    fun getMEalDetails(@Query("i") id: String): Call<Meals>

}