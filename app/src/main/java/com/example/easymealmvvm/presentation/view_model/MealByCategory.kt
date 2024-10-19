package com.example.easymealmvvm.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easymealmvvm.data.model.PopularMeal
import com.example.easymealmvvm.data.model.PopularMeals
import com.example.easymealmvvm.data.remote.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealByCategory : ViewModel() {


    private var _mealByCategoryLiveData = MutableLiveData<List<PopularMeal>>()
    val mealByCategoryLiveData: LiveData<List<PopularMeal>> = _mealByCategoryLiveData

    fun getMealsByCategory(catName: String) {
        RetrofitInstance.api.getPopularMeals(catName).enqueue(object : Callback<PopularMeals> {
            override fun onResponse(call: Call<PopularMeals>, response: Response<PopularMeals>) {
                if (response.body() != null){
                    _mealByCategoryLiveData.value = response.body()!!.meals
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