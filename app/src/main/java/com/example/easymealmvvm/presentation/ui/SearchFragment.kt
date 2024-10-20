package com.example.easymealmvvm.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.easymealmvvm.R
import com.example.easymealmvvm.databinding.FragmentSearchBinding
import com.example.easymealmvvm.presentation.adapter.FavoriteMealsAdapter
import com.example.easymealmvvm.presentation.view_model.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).mainViewModel

        binding.icSearch.setOnClickListener {
            searchForMeal(binding.edSearch.text.toString())
        }

        var searchJob: Job? = null
        binding.edSearch.addTextChangedListener {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                searchForMeal(it.toString())
            }
        }


    }

    private fun searchForMeal(searchQuery: String) {
        viewModel.searchMeals(searchQuery)
        viewModel.searchMealsLiveData.observe(viewLifecycleOwner) { mealsList ->
           if(!mealsList.isNullOrEmpty()) {
                binding.searchedMealCard.visibility = View.VISIBLE
                Glide.with(this@SearchFragment).load(mealsList[0].strMealThumb)
                    .into(binding.imgSearchedMeal)
                binding.tvSearchedMeal.text = mealsList[0].strMeal
            }

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

}