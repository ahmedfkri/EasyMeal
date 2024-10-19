package com.example.easymealmvvm.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easymealmvvm.data.local.MealDatabase
import com.example.easymealmvvm.data.model.Categories
import com.example.easymealmvvm.data.model.Category
import com.example.easymealmvvm.data.model.Meal
import com.example.easymealmvvm.data.model.Meals
import com.example.easymealmvvm.data.model.PopularMeal
import com.example.easymealmvvm.data.model.PopularMeals
import com.example.easymealmvvm.data.remote.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val mealDatabase: MealDatabase
) : ViewModel() {

    private var _randomMealLiveData = MutableLiveData<Meal>()
    val randomMealLiveData: LiveData<Meal> = _randomMealLiveData

    private var _popularMealsLiveData = MutableLiveData<List<PopularMeal>>()
    val popularMealsLiveData: LiveData<List<PopularMeal>> = _popularMealsLiveData

    private val _categoriesLiveData = MutableLiveData<List<Category>>()
    val categoriesLiveData: LiveData<List<Category>> = _categoriesLiveData

    private val _favoriteMealsLiveData = mealDatabase.mealDAO().getAllMeals()
    val favoriteMealsLiveData: LiveData<List<Meal>> = _favoriteMealsLiveData



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

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<Categories>{
            override fun onResponse(call: Call<Categories>, response: Response<Categories>) {
                if (response.body() != null){
                    _categoriesLiveData.value = response.body()!!.categories
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<Categories>, t: Throwable) {
                Log.d("HomeViewModel", t.message.toString())
            }

        })
    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDAO().delete(meal)
        }
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDAO().upsert(meal)
        }
    }





}