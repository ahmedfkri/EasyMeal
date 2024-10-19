package com.example.easymealmvvm.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.easymealmvvm.R
import com.example.easymealmvvm.core.Constants.CATEGORY_NAME
import com.example.easymealmvvm.databinding.ActivityMealByCategoryBinding
import com.example.easymealmvvm.presentation.adapter.MealsByCategoryAdapter
import com.example.easymealmvvm.presentation.view_model.MealByCategory

class MealByCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealByCategoryBinding

    private lateinit var mealByCategoryAdapter: MealsByCategoryAdapter

    private lateinit var mealByCategoryViewModel: MealByCategory

    private lateinit var catName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMealByCategoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mealByCategoryViewModel = ViewModelProvider(this)[MealByCategory::class.java]
        mealByCategoryAdapter = MealsByCategoryAdapter()

        getInformationFromIntent()
        getMealsByCategory()
    }

    @SuppressLint("SetTextI18n")
    private fun getMealsByCategory() {
        mealByCategoryViewModel.getMealsByCategory(catName)
        lifecycleScope.launchWhenCreated {
            mealByCategoryViewModel.mealByCategoryLiveData.observe(this@MealByCategoryActivity) { mealsList ->
                mealByCategoryAdapter.differ.submitList(mealsList)
                binding.rvCategoryItems.adapter = mealByCategoryAdapter
                binding.txtCatCount.text = "${catName} : " +mealByCategoryAdapter.itemCount.toString()
            }
        }

    }


    private fun getInformationFromIntent() {
        val intent = intent
        catName = intent.getStringExtra(CATEGORY_NAME).toString()
    }
}