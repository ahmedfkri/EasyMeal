package com.example.easymealmvvm.data.remote

import com.example.easymealmvvm.data.model.Categories
import com.example.easymealmvvm.data.model.Meals
import com.example.easymealmvvm.data.model.PopularMeal
import com.example.easymealmvvm.data.model.PopularMeals
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsAPI {


    @GET("random.php")
    fun getRandomMeal(): Call<Meals>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id: String): Call<Meals>

    @GET("filter.php?")
    fun getPopularMeals(@Query("c") categoryName: String): Call<PopularMeals>

    @GET("categories.php")
    fun getCategories(): Call<Categories>


    @GET("search.php?")
    fun searchMeals(@Query("s") searchQuery: String): Call<Meals>




}