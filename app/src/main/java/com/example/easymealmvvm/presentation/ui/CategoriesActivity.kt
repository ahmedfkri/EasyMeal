package com.example.easymealmvvm.presentation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.easymealmvvm.R
import com.example.easymealmvvm.core.Constants.CATEGORY_NAME
import com.example.easymealmvvm.databinding.ActivityCategoriesBinding
import com.example.easymealmvvm.presentation.adapter.MealsByCategoryAdapter
import com.example.easymealmvvm.presentation.view_model.CategoriesViewModel

class CategoriesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoriesBinding

    lateinit var mealByCategoryAdapter: MealsByCategoryAdapter

    lateinit var categoriesViewModel: CategoriesViewModel

    lateinit var catName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        categoriesViewModel = ViewModelProvider(this)[CategoriesViewModel::class.java]
        mealByCategoryAdapter = MealsByCategoryAdapter()

        getInformationFromIntent()
        getMealsByCategory()
    }

    private fun getMealsByCategory() {
        categoriesViewModel.getMealsByCategory(catName)
        lifecycleScope.launchWhenCreated {
            categoriesViewModel.mealByCategoryLiveData.observe(this@CategoriesActivity) { mealsList ->
                mealByCategoryAdapter.setMealList(mealsList)
                binding.rvCategoryItems.adapter = mealByCategoryAdapter
            }
        }
    }


    private fun getInformationFromIntent() {
        val intent = intent
        catName = intent.getStringExtra(CATEGORY_NAME).toString()
        binding.txtCatCount.text = "$catName : Count"

    }
}