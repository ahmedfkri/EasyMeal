package com.example.easymealmvvm.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.easymealmvvm.core.Constants.CATEGORY_NAME
import com.example.easymealmvvm.core.Constants.MEAL_ID
import com.example.easymealmvvm.core.Constants.MEAL_NAME
import com.example.easymealmvvm.core.Constants.MEAL_THUMB
import com.example.easymealmvvm.data.model.Meal

import com.example.easymealmvvm.databinding.FragmentHomeBinding
import com.example.easymealmvvm.presentation.adapter.CategoriesAdapter
import com.example.easymealmvvm.presentation.adapter.PopularMealsAdapter
import com.example.easymealmvvm.presentation.view_model.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var randomMeal: Meal

    private lateinit var popularMealsAdapter: PopularMealsAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        popularMealsAdapter = PopularMealsAdapter()
        categoriesAdapter = CategoriesAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getRandomMeal()
        onRandomMealClick()

        setupPopularRecyclerView()
        getPopularMeals()
        onPopularMealClick()

        setUpCategoryRecyclerView()
        getCategories()
        onCategoryClick()
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { cat->
            Intent(activity, CategoriesActivity::class.java).also {
                it.putExtra(CATEGORY_NAME, cat.strCategory)
                startActivity(it)
            }

        }
    }

    private fun getCategories() {
        homeViewModel.getCategories()
        homeViewModel.categoriesLiveData.observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.setCategoryList(categories)
            categoriesAdapter.notifyDataSetChanged()
        }

    }

    private fun setUpCategoryRecyclerView() {
        binding.rvCategory.adapter = categoriesAdapter
        binding.rvCategory.layoutManager =
            GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
    }

    private fun onPopularMealClick() {
        popularMealsAdapter.onItemClick = { meal ->
            Intent(activity, MealActivity::class.java).also {
                it.putExtra(MEAL_ID, meal.idMeal)
                it.putExtra(MEAL_NAME, meal.strMeal)
                it.putExtra(MEAL_THUMB, meal.strMealThumb)
                startActivity(it)
            }


        }
    }

    private fun setupPopularRecyclerView() {
        binding.rvOverPopular.apply {
            adapter = popularMealsAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        }
    }

    private fun getPopularMeals() {
        homeViewModel.getPopularMeals()
        homeViewModel.popularMealsLiveData.observe(viewLifecycleOwner) { mealList ->
            popularMealsAdapter.setMealList(mealList)
            popularMealsAdapter.notifyDataSetChanged()

        }
    }

    private fun onRandomMealClick() {
        binding.cardRandomMeal.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)

        }

    }

    private fun getRandomMeal() {
        homeViewModel.getRandomMeal()
        homeViewModel.randomMealLiveData.observe(viewLifecycleOwner) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal.strMealThumb).into(binding.imgRandomMeal)
            randomMeal = meal
        }
    }



}