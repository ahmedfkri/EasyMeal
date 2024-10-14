package com.example.easymealmvvm.data.remote

import com.example.easymealmvvm.data.Meals
import retrofit2.Call
import retrofit2.http.GET

interface MealsAPI {


    @GET("random.php")
    fun getRandomMeal(): Call<Meals>

}