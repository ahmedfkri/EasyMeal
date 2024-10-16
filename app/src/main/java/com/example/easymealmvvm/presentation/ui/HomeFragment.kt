package com.example.easymealmvvm.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easymealmvvm.core.Constants.MEAL_ID
import com.example.easymealmvvm.core.Constants.MEAL_NAME
import com.example.easymealmvvm.core.Constants.MEAL_THUMB
import com.example.easymealmvvm.data.Meal

import com.example.easymealmvvm.databinding.FragmentHomeBinding
import com.example.easymealmvvm.presentation.view_model.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var randomMeal: Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getRandomMeal()
        onRandomMealClick()
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
            randomMeal= meal
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

}