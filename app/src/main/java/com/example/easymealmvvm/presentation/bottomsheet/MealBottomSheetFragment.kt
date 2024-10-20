package com.example.easymealmvvm.presentation.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.easymealmvvm.core.Constants.MEAL_ID
import com.example.easymealmvvm.core.Constants.MEAL_NAME
import com.example.easymealmvvm.core.Constants.MEAL_THUMB
import com.example.easymealmvvm.databinding.FragmentMealBottomSheetBinding
import com.example.easymealmvvm.presentation.ui.MainActivity
import com.example.easymealmvvm.presentation.ui.MealActivity
import com.example.easymealmvvm.presentation.view_model.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment




class MealBottomSheetFragment : BottomSheetDialogFragment() {
    private var mealId: String = ""
    private var mealName: String = ""
    private var mealThumb: String = ""
    private lateinit var viewModel: MainViewModel

    lateinit var binding: FragmentMealBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)!!
        }

        viewModel = (activity as MainActivity).mainViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomSheet.isEnabled = false
        getMealById(mealId)
        onBottomSheetDialogClick()

    }

    private fun onBottomSheetDialogClick() {
        binding.bottomSheet.setOnClickListener {
            if (mealId.isEmpty() || mealName.isEmpty() || mealThumb.isEmpty()) {
                Toast.makeText(activity, "Data not loaded yet", Toast.LENGTH_SHORT).show()
            } else {
                Intent(activity, MealActivity::class.java).also {
                    it.putExtra(MEAL_ID, mealId)
                    it.putExtra(MEAL_NAME, mealName)
                    it.putExtra(MEAL_THUMB, mealThumb)
                    startActivity(it)
                }
            }
        }
    }

    private fun getMealById(mealId: String?) {
        viewModel.getMealById(mealId!!)
        viewModel.mealByIdLiveData.observe(viewLifecycleOwner) { meal ->
            binding.apply {
                Glide.with(this@MealBottomSheetFragment)
                    .load(meal.strMealThumb)
                    .into(imgMealBottom)
                txtMealName.text = meal.strMeal
                txtCategory.text = meal.strCategory
                txtLocation.text = meal.strArea

                mealName = meal.strMeal ?: ""
                mealThumb = meal.strMealThumb ?: " "

                binding.bottomSheet.isEnabled = true

            }

        }
    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)
                }
            }
    }
}