package com.example.easymealmvvm.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.easymealmvvm.data.local.MealDatabase

class MealViewModelFactory(
    private val mealDatabase: MealDatabase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(mealDatabase) as T
    }

}