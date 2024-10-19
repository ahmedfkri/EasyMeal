package com.example.easymealmvvm.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.example.easymealmvvm.core.Constants.CATEGORY_NAME
import com.example.easymealmvvm.databinding.FragmentCategoriesBinding
import com.example.easymealmvvm.presentation.adapter.CategoriesAdapter
import com.example.easymealmvvm.presentation.view_model.MainViewModel

class CategoriesFragment : Fragment() {

    lateinit var binding: FragmentCategoriesBinding
    private lateinit var  viewModel: MainViewModel
    private lateinit var  categoriesAdapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel=(activity as MainActivity).mainViewModel

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpCategoriesRecyclerView()
        getCategories()
        onCategoryClick()
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { cat->
            Intent(activity, MealByCategoryActivity::class.java).also{
                it.putExtra(CATEGORY_NAME, cat.strCategory)
                startActivity(it)
            }

        }
    }

    private fun setUpCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategories.adapter = categoriesAdapter
    }

    private fun getCategories() {
        viewModel.categoriesLiveData.observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.differ.submitList(categories)
        }

    }
}