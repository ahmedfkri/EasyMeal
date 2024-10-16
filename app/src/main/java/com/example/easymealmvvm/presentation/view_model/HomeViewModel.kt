package com.example.easymealmvvm.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easymealmvvm.data.model.Meal
import com.example.easymealmvvm.data.model.Meals
import com.example.easymealmvvm.data.model.PopularMeal
import com.example.easymealmvvm.data.model.PopularMeals
import com.example.easymealmvvm.data.remote.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel() : ViewModel() {

    private var _randomMealLiveData = MutableLiveData<Meal>()
    val randomMealLiveData: LiveData<Meal> = _randomMealLiveData

    private var _popularMealsLiveData = MutableLiveData<List<PopularMeal>>()
    val popularMealsLiveData: LiveData<List<PopularMeal>> = _popularMealsLiveData

    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<Meals> {
            override fun onResponse(call: Call<Meals>, response: Response<Meals>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    _randomMealLiveData.value = randomMeal
                } else return
            }

            override fun onFailure(call: Call<Meals>, t: Throwable) {
                Log.d("HomeViewModel", t.message.toString())
            }
        })
    }

    fun getPopularMeals() {
        RetrofitInstance.api.getPopularMeals("Seafood").enqueue(object : Callback<PopularMeals> {
            override fun onResponse(call: Call<PopularMeals>, response: Response<PopularMeals>) {
                if (response.body() != null){
                    _popularMealsLiveData.value = response.body()!!.meals
                }else{
                    return
                }

            }

            override fun onFailure(call: Call<PopularMeals>, t: Throwable) {
                Log.d("HomeViewModel", t.message.toString())
            }

        })

    }
}