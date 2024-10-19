package com.example.easymealmvvm.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.easymealmvvm.data.local.MealDatabase

class MainViewModelFactory (
    val mealDatabase: MealDatabase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(mealDatabase) as T
    }

}