package com.example.easymealmvvm.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

import com.example.easymealmvvm.core.Constants.MEAL_ID
import com.example.easymealmvvm.core.Constants.MEAL_NAME
import com.example.easymealmvvm.core.Constants.MEAL_THUMB
import com.example.easymealmvvm.databinding.FragmentFavoritesBinding
import com.example.easymealmvvm.presentation.adapter.FavoriteMealsAdapter
import com.example.easymealmvvm.presentation.view_model.MainViewModel
import com.google.android.material.snackbar.Snackbar


class FavoritesFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    lateinit var binding: FragmentFavoritesBinding
    lateinit var favoriteMealsAdapter: FavoriteMealsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).mainViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpFavoriteRecyclerView()
        getFavoriteMeals()
        onFavoriteMealClick()

        //Swipe to delete
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT  or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteMeal(favoriteMealsAdapter.differ.currentList[position])
                favoriteMealsAdapter.notifyItemChanged(position)
                showSnack(position)
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavoriteMeals)

    }

    private fun showSnack(position: Int) {
        Snackbar.make(requireView(), "Meal Deleted", Snackbar.LENGTH_SHORT).setAction(
            "Undo",
            View.OnClickListener {
                viewModel.insertMeal(favoriteMealsAdapter.differ.currentList[position])
            }
        ).show()
    }

    private fun onFavoriteMealClick() {
        favoriteMealsAdapter.onItemClick = { meal ->
            Intent(activity, MealActivity::class.java).also {
                it.putExtra(MEAL_ID, meal.idMeal)
                it.putExtra(MEAL_NAME, meal.strMeal)
                it.putExtra(MEAL_THUMB, meal.strMealThumb)
                startActivity(it)
            }
        }
    }

    private fun setUpFavoriteRecyclerView() {
        favoriteMealsAdapter = FavoriteMealsAdapter()
        binding.rvFavoriteMeals.adapter = favoriteMealsAdapter
    }

    private fun getFavoriteMeals() {
        viewModel.favoriteMealsLiveData.observe(viewLifecycleOwner) { meals ->
            favoriteMealsAdapter.differ.submitList(meals)
        }
    }

}