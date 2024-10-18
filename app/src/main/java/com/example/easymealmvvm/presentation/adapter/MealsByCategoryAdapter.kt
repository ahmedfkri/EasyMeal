package com.example.easymealmvvm.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easymealmvvm.data.model.PopularMeal
import com.example.easymealmvvm.databinding.MealListItemBinding

class MealsByCategoryAdapter : RecyclerView.Adapter<MealsByCategoryAdapter.CategoryViewHolder>() {

    private var mealsList = ArrayList<PopularMeal>()

    fun setMealList(mealList: List<PopularMeal>) {
        this.mealsList = mealList as ArrayList<PopularMeal>
        notifyDataSetChanged()
    }

    class CategoryViewHolder(val binding: MealListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            MealListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = mealsList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val meal = mealsList[position]
        holder.binding.txtMealName.text = meal.strMeal
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imgMeal)

    }


}