package com.example.easymealmvvm.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easymealmvvm.data.Meal
import com.example.easymealmvvm.data.Meals
import com.example.easymealmvvm.data.remote.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel : ViewModel() {

    private var _mealDetailsLiveData = MutableLiveData<Meal>()
    val mealDetailsLiveData : LiveData<Meal> = _mealDetailsLiveData

    fun getMealDetails(id: String){
        RetrofitInstance.api.getMEalDetails(id).enqueue(object : Callback<Meals> {
            override fun onResponse(call: Call<Meals>, response: Response<Meals>) {
                if(response.body() != null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                    _mealDetailsLiveData.value=randomMeal
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<Meals>, t: Throwable) {
                Log.d("MealViewModel", t.message.toString())
            }
        })
    }

}